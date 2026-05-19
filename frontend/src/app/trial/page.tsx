'use client';
/* eslint-disable react-hooks/set-state-in-effect */

import { useState, useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';
import { motion } from 'framer-motion';
import toast, { Toaster } from 'react-hot-toast';

const portraitMap: Record<string, string> = {
  // SUPERVIVIENTES
  "LeonKennedy": "/leon.jpg",
  "SteveHarrington": "/steve.jpg",
  "FengMin": "/fenming.jpg",
  "SableWard": "/sableward.jpg",
  "Mikaela": "/mikaelareid.jpg",
  "MikaelaReid": "/mikaelareid.jpg",
  "AdaWong": "/adawong.jpg",
  "LaraCroft": "/laracroft.jpg",
  "Nancy": "/nancy.jpg",
  "NancyWheeler": "/nancy.jpg",
  // KILLERS
  "GhostFace": "/ghostface.jpg",
  "Legion": "/legion.jpg",
  "Onryo": "/onryo.jpg",
  "Animatronico": "/animatronico.jpg",
  "Animatrónico": "/animatronico.jpg",
  "Ghoul": "/ghoul.jpg",
  "Chucky": "/chuckyf.jpg",
  "Wesker": "/wesker.jpg",
  "AlbertWesker": "/wesker.jpg",
  "LaCerda": "/cerda.jpg",
  "DEFAULT": "/dbd_logo.png"
};

interface Perk {
  nombre: string;
  usos: number;
}

interface CharacterState {
  nombrePersonaje: string;
  vidaActual: number;
  vidaMax: number;
  perks: Perk[];
}

interface GameState {
  supervivientes: CharacterState[];
  killers: CharacterState[];
  logs: string[];
  partidaTerminada: boolean;
  ganador: string | null;
  modoJuego?: string;
  decidedAction?: string;
  decidedTargetIndex?: number;
  decidedPerkIndex?: number;
}

interface SaveSlot {
  id: number;
  vacia: boolean;
  modoJuego: string;
  ronda: number;
  survsVivos: number;
  killersVivos: number;
}

function HealthBar({ value, max }: { value: number, max: number }) {
  const pct = Math.max(0, Math.min(100, (value / max) * 100));
  const isLow = pct <= 30;
  return (
    <div className={`w-full h-2 rounded-full overflow-hidden bg-zinc-900 border border-zinc-800 ${isLow ? 'animate-pulse shadow-[0_0_10px_rgba(220,38,38,0.5)]' : ''}`}>
      <div className={`h-full transition-all duration-500 ${isLow ? 'bg-red-600 shadow-[0_0_8px_rgba(220,38,38,0.8)]' : 'bg-green-500 shadow-[0_0_8px_rgba(34,197,94,0.8)]'}`} style={{ width: `${pct}%` }}></div>
    </div>
  );
}

function CharacterCard({ char, isMyTurn, isKiller }: { char: CharacterState, isMyTurn: boolean, isKiller: boolean }) {
  const [prevHp, setPrevHp] = useState(char.vidaActual);
  const [isTakingDamage, setIsTakingDamage] = useState(false);

  useEffect(() => {
    if (char.vidaActual < prevHp) {
      setIsTakingDamage(true);
      const t = setTimeout(() => setIsTakingDamage(false), 500);
      setPrevHp(char.vidaActual);
      return () => clearTimeout(t);
    } else if (char.vidaActual > prevHp) {
      setPrevHp(char.vidaActual);
    }
  }, [char.vidaActual, prevHp]);

  const isDead = char.vidaActual <= 0;

  const baseClasses = isKiller
    ? 'border-red-900/50 bg-red-950/20 hover:shadow-[0_0_15px_rgba(220,38,38,0.3)]'
    : 'border-blue-900/50 bg-blue-950/20 hover:shadow-[0_0_15px_rgba(34,197,94,0.3)]';

  const turnClasses = isKiller
    ? 'border-red-500 shadow-[0_0_15px_rgba(220,38,38,0.6)] animate-pulse'
    : 'border-green-500 shadow-[0_0_15px_rgba(34,197,94,0.5)] animate-pulse';

  const damageClasses = 'border-red-600 bg-red-900/40 shadow-[0_0_20px_rgba(220,38,38,0.8)]';

  let currentClasses = baseClasses;
  if (isDead) currentClasses = 'border-zinc-800 bg-zinc-900/30 opacity-50';
  else if (isTakingDamage) currentClasses = damageClasses;
  else if (isMyTurn) currentClasses = turnClasses;

  return (
    <motion.div
      animate={isTakingDamage ? { x: [-5, 5, -5, 5, 0], transition: { duration: 0.4 } } : { x: 0 }}
      whileHover={{ scale: 1.05, y: -5 }}
      className={`p-3 rounded-lg border transition-all cursor-default ${currentClasses}`}
    >
      <div className="flex justify-between items-center mb-2 font-[family-name:var(--font-special-elite)]">
        <span className={`text-sm font-bold ${isDead ? 'text-zinc-600 line-through' : (isKiller ? 'text-red-200' : 'text-blue-200')}`}>{char.nombrePersonaje}</span>
        <span className={`text-xs font-black ${isDead ? 'text-zinc-600' : (isKiller ? 'text-red-400 drop-shadow-[0_0_5px_rgba(220,38,38,0.8)]' : 'text-green-400 drop-shadow-[0_0_5px_rgba(34,197,94,0.8)]')}`}>{isDead ? 'MUERTO' : `${char.vidaActual} HP`}</span>
      </div>
      {!isDead && <HealthBar value={char.vidaActual} max={char.vidaMax} />}
    </motion.div>
  );
}

export default function TrialPage() {
  const router = useRouter();

  const [gameState, setGameState] = useState<GameState | null>(null);
  const [combatLogs, setCombatLogs] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const [isSurviTurn, setIsSurviTurn] = useState(true);
  const [currentIdx, setCurrentIdx] = useState(0);

  const [activeMode, setActiveMode] = useState<'NONE' | 'ATACAR' | 'PERK'>('NONE');
  const [previewTarget, setPreviewTarget] = useState<{ charKey: string; name: string; image: string } | null>(null);
  const [isAnimating, setIsAnimating] = useState(false);
  const [animState, setAnimState] = useState<'IDLE' | 'ATTACK' | 'PERK' | 'DAMAGE' | 'DEFEND'>('IDLE');

  const [isSaveModalOpen, setIsSaveModalOpen] = useState(false);
  const [saves, setSaves] = useState<SaveSlot[]>([]);
  const [isLoadingSaves, setIsLoadingSaves] = useState(false);

  const logsEndRef = useRef<HTMLDivElement>(null);

  // Original keys from localStorage to match portraits
  const [survKeys, setSurvKeys] = useState<string[]>([]);
  const [killerKeys, setKillerKeys] = useState<string[]>([]);

  const sendAction = async (tipoAccion: string, objetivoIndex = -1, perkIndex = -1) => {
    if (isAnimating || !gameState || gameState.partidaTerminada) return;

    setIsAnimating(true);
    setActiveMode('NONE');

    const globalAtacanteIdx = isSurviTurn ? currentIdx : (gameState.supervivientes.length + currentIdx);
    const safeObjetivoIndex = objetivoIndex === -1 ? 0 : objetivoIndex;

    const rivales = isSurviTurn ? gameState.killers : gameState.supervivientes;

    if (tipoAccion !== 'AUTO') {
      // Manual action: trigger pre-animations
      if (safeObjetivoIndex < rivales.length) {
        const targetChar = rivales[safeObjetivoIndex];
        const fixedKey = targetChar.nombrePersonaje.replace(/\s+/g, '');
        setPreviewTarget({
          charKey: fixedKey,
          name: targetChar.nombrePersonaje,
          image: portraitMap[fixedKey] || portraitMap['DEFAULT']
        });
      }

      if (tipoAccion === 'ATACAR') {
        setAnimState('ATTACK');
        await new Promise(r => setTimeout(r, 600));
        setAnimState('DAMAGE');
        await new Promise(r => setTimeout(r, 800));
        setAnimState('IDLE');
      } else if (tipoAccion === 'PERK') {
        setAnimState('PERK');
        await new Promise(r => setTimeout(r, 700));
        setAnimState('DAMAGE');
        await new Promise(r => setTimeout(r, 800));
        setAnimState('IDLE');
      } else if (tipoAccion === 'DEFENDER') {
        setAnimState('DEFEND');
        await new Promise(r => setTimeout(r, 800));
        setAnimState('IDLE');
      }
      setPreviewTarget(null);
    }

    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch('/api/game/action', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        },
        body: JSON.stringify({
          tipoAccion,
          atacanteIndex: globalAtacanteIdx,
          objetivoIndex: safeObjetivoIndex,
          perkIndex
        })
      });

      const data: GameState = await res.json();

      if (tipoAccion === 'AUTO' && data) {
        const decAction = data.decidedAction;
        const decTargetIdx = data.decidedTargetIndex ?? -1;

        if (decAction === 'ATACAR') {
          const safeDecTarget = decTargetIdx >= 0 && decTargetIdx < rivales.length ? decTargetIdx : 0;
          const targetChar = rivales[safeDecTarget];
          if (targetChar) {
            const fixedKey = targetChar.nombrePersonaje.replace(/\s+/g, '');
            setPreviewTarget({
              charKey: fixedKey,
              name: targetChar.nombrePersonaje,
              image: portraitMap[fixedKey] || portraitMap['DEFAULT']
            });
          }
          setAnimState('ATTACK');
          await new Promise(r => setTimeout(r, 600));
          setAnimState('DAMAGE');
          await new Promise(r => setTimeout(r, 800));
          setAnimState('IDLE');
          setPreviewTarget(null);
        } else if (decAction === 'PERK') {
          const safeDecTarget = decTargetIdx >= 0 && decTargetIdx < rivales.length ? decTargetIdx : 0;
          const targetChar = rivales[safeDecTarget];
          if (targetChar) {
            const fixedKey = targetChar.nombrePersonaje.replace(/\s+/g, '');
            setPreviewTarget({
              charKey: fixedKey,
              name: targetChar.nombrePersonaje,
              image: portraitMap[fixedKey] || portraitMap['DEFAULT']
            });
          }
          setAnimState('PERK');
          await new Promise(r => setTimeout(r, 700));
          setAnimState('DAMAGE');
          await new Promise(r => setTimeout(r, 800));
          setAnimState('IDLE');
          setPreviewTarget(null);
        } else if (decAction === 'DEFENDER') {
          setAnimState('DEFEND');
          await new Promise(r => setTimeout(r, 800));
          setAnimState('IDLE');
        } else {
          setAnimState('ATTACK');
          await new Promise(r => setTimeout(r, 450));
          setAnimState('IDLE');
        }
      }

      setGameState(data);
      if (data.logs && data.logs.length > 0) {
        setCombatLogs(prev => [...prev, ...data.logs]);
      }

      if (!data.partidaTerminada) {
        setCurrentIdx(prev => prev + 1);
      }
    } catch (e) {
      console.error(e);
      setCombatLogs(prev => [...prev, '> Error conectando con el servidor.']);
    } finally {
      setIsAnimating(false);
    }
  };

  useEffect(() => {
    // Scroll to bottom of logs
    logsEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [combatLogs]);

  useEffect(() => {
    const isResume = localStorage.getItem('resumeGame') === 'true';
    const sKeys = JSON.parse(localStorage.getItem('selectedSurvs') || '[]');
    const kKeys = JSON.parse(localStorage.getItem('selectedKillers') || '[]');

    if (!isResume && (sKeys.length !== 3 || kKeys.length !== 3)) {
      router.push('/play');
      return;
    }

    if (!isResume) {
      setSurvKeys(sKeys);
      setKillerKeys(kKeys);
    }

    const initTrial = async () => {
      try {
        const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];

        const endpoint = isResume ? '/api/game/state' : '/api/game/start-manual';
        const method = isResume ? 'GET' : 'POST';
        const gameMode = localStorage.getItem('selectedMode') || 'manual';
        const body = isResume ? undefined : JSON.stringify({ supervivientes: sKeys, killers: kKeys, modo: gameMode });

        const res = await fetch(endpoint, {
          method,
          headers: {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
          },
          body
        });
        const data: GameState = await res.json();

        if (data && data.supervivientes) {
          setGameState(data);
          if (data.logs) setCombatLogs(data.logs);

          if (isResume) {
            // Reconstruir las keys a partir de los nombres
            const sKeysLoaded = data.supervivientes.map((s) => s.nombrePersonaje.replace(/\s+/g, ''));
            const kKeysLoaded = data.killers.map((k) => k.nombrePersonaje.replace(/\s+/g, ''));
            setSurvKeys(sKeysLoaded);
            setKillerKeys(kKeysLoaded);
            localStorage.removeItem('resumeGame');
          }
        } else {
          setGameState(null);
          setCombatLogs(['Error crítico: El backend no devolvió una partida válida. (¿Está encendido?)']);
        }
      } catch (err) {
        console.error(err);
        setGameState(null);
        setCombatLogs(['Error: No se pudo conectar a la Entidad.']);
      } finally {
        setIsLoading(false);
      }
    };

    initTrial();
  }, [router]);

  // Turn Evaluation Logic
  useEffect(() => {
    if (!gameState || gameState.partidaTerminada) return;

    const equipoActual = isSurviTurn ? gameState.supervivientes : gameState.killers;
    let newIdx = currentIdx;

    while (newIdx < equipoActual.length && equipoActual[newIdx].vidaActual <= 0) {
      newIdx++;
    }

    if (newIdx >= equipoActual.length) {
      setIsSurviTurn(!isSurviTurn);
      setCurrentIdx(0);
    } else if (newIdx !== currentIdx) {
      setCurrentIdx(newIdx);
    }
  }, [gameState, currentIdx, isSurviTurn]);

  // Auto-play game loop for automatic mode
  useEffect(() => {
    if (!gameState || gameState.partidaTerminada || isLoading || isAnimating || isSaveModalOpen) return;

    if (gameState.modoJuego === 'automatico') {
      const timer = setTimeout(() => {
        sendAction('AUTO');
      }, 1600);
      return () => clearTimeout(timer);
    }
  }, [gameState, isAnimating, isLoading, isSaveModalOpen, currentIdx, isSurviTurn]);

  if (isLoading) {
    return (
      <div className="min-h-screen bg-zinc-950 flex items-center justify-center flex-col text-white">
        <div className="w-16 h-16 border-4 border-red-600 border-t-transparent rounded-full animate-spin mb-4"></div>
        <p className="tracking-[0.2em] animate-pulse">La Entidad está preparando la Prueba...</p>
      </div>
    );
  }

  if (!gameState || !gameState.supervivientes || !gameState.killers) {
    return (
      <div className="min-h-screen bg-zinc-950 flex flex-col items-center justify-center text-white p-6">
        <h2 className="text-4xl font-black text-red-600 tracking-widest mb-4">ERROR DE CONEXIÓN</h2>
        <p className="text-zinc-400 text-center mb-8">{combatLogs[0] || 'No se pudo iniciar la partida. Verifica que el servidor Java esté encendido.'}</p>
        <button onClick={() => router.push('/dashboard')} className="px-6 py-3 bg-red-700 hover:bg-red-600 rounded font-bold transition-colors">Volver al Dashboard</button>
      </div>
    );
  }

  const equipoActual = isSurviTurn ? gameState.supervivientes : gameState.killers;
  const actor = (currentIdx >= 0 && currentIdx < equipoActual.length ? equipoActual[currentIdx] : null) || equipoActual[0] || gameState.supervivientes[0];

  const actorKey = actor?.nombrePersonaje?.replace(/\s+/g, '') || 'DEFAULT';
  const actorImg = portraitMap[actorKey] || portraitMap['DEFAULT'];

  const handleModeAction = (targetIdx: number, perkIdx: number = -1) => {
    sendAction(activeMode, targetIdx, perkIdx);
  };

  const openSaveModal = async () => {
    setIsSaveModalOpen(true);
    setIsLoadingSaves(true);
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch('/api/game/saves', {
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.ok) {
        const data = await res.json();
        setSaves(data);
      } else {
        toast.error("Error al obtener ranuras de guardado.");
      }
    } catch (error) {
      toast.error("Error de red al conectar.");
    } finally {
      setIsLoadingSaves(false);
    }
  };

  const handleSaveGame = async (slotId: number) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch('/api/game/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        },
        body: JSON.stringify({ slot: slotId })
      });
      if (res.ok) {
        toast.success(`El progreso ha sido sellado en la Ranura ${slotId}.`);
        setIsSaveModalOpen(false);
        setTimeout(() => router.push('/dashboard'), 1500);
      } else {
        console.error(`Error al guardar. Status: ${res.status}`);
        toast.error('Error al guardar la partida.');
      }
    } catch (e) {
      console.error('Catch error en handleSaveGame:', e);
      toast.error('Fallo de red al conectar con el servidor.');
    }
  };

  const rivales = isSurviTurn ? gameState.killers : gameState.supervivientes;
  const rivalKeys = isSurviTurn ? killerKeys : survKeys;

  return (
    <div className="h-screen w-screen flex flex-col bg-zinc-950 font-sans text-white overflow-hidden relative selection:bg-red-900/30">
      <Toaster position="top-right" />
      <div className="absolute inset-0 bg-[url('https://c4.wallpaperflare.com/wallpaper/528/773/953/dead-by-daylight-logo-4k-wallpaper-preview.jpg')] bg-cover bg-center opacity-10"></div>

      <div className="relative z-10 flex-1 flex flex-col overflow-hidden">

        <div className="flex-1 flex flex-col md:flex-row overflow-hidden">

          {/* LEFT SIDEBAR - SURVIVORS */}
          <aside className="w-full md:w-64 bg-black/60 border-r border-zinc-800 p-4 overflow-y-auto hidden md:block">
            <h4 className="text-2xl font-normal text-blue-500 tracking-widest mb-4 border-b border-blue-900/50 pb-2 font-[family-name:var(--font-horroroid)]">SUPERVIVIENTES</h4>
            <div className="space-y-2">
              {gameState.supervivientes.slice(0, 3).map((s, idx) => {
                const isDead = s.vidaActual <= 0;
                const isMyTurn = isSurviTurn && currentIdx === idx && !isDead;
                return (
                  <CharacterCard key={`${s.nombrePersonaje}-${idx}`} char={s} isMyTurn={isMyTurn} isKiller={false} />
                );
              })}
            </div>
          </aside>

          {/* CENTER BATTLE STAGE & LOGS */}
          <main className="flex-1 flex flex-col bg-zinc-950/50 backdrop-blur-sm relative">
            {/* Stage */}
            <div className="flex-1 relative flex items-center justify-center min-h-[300px]">

              <div className="flex flex-col md:flex-row items-center justify-center gap-6 w-full px-4 md:px-10 h-full">
                {/* ACTOR (Attacker) */}
                <div className={`relative flex flex-col items-center transition-all duration-500 ease-in-out ${animState === 'ATTACK' ? 'translate-x-4 scale-110 z-50 drop-shadow-[0_0_30px_rgba(220,38,38,0.7)]' : ''} ${animState === 'PERK' ? '-translate-y-4 scale-110 z-50 drop-shadow-[0_0_35px_rgba(168,85,247,0.9)] animate-pulse' : ''} ${animState === 'DEFEND' ? 'scale-95 opacity-80 brightness-150 drop-shadow-[0_0_30px_rgba(59,130,246,1)]' : ''}`}>
                  <div className={`w-40 h-56 md:w-56 md:h-72 rounded-xl overflow-hidden border-4 ${animState === 'PERK' ? 'border-purple-500 shadow-[0_0_45px_rgba(168,85,247,0.9)]' : isSurviTurn ? 'border-blue-500 shadow-[0_0_40px_rgba(59,130,246,0.6)]' : 'border-red-600 shadow-[0_0_40px_rgba(220,38,38,0.6)]'}`}>
                    {/* eslint-disable-next-line @next/next/no-img-element */}
                    <img
                      src={actorImg}
                      alt="Actor"
                      className={`w-full h-full object-cover 
                        ${actorKey === 'AdaWong' ? 'object-center scale-[1.5]' :
                          actorKey === 'Onryo' ? 'object-bottom scale-[1.2] -translate-y-10' :
                            (actorKey === 'Animatronico' || actorKey === 'Animatrónico') ? 'object-[42%_30%] scale-[1.25]' :
                              'object-top'}`}
                    />
                  </div>
                  <div className="mt-4 text-white bg-transparent font-normal tracking-wide text-xl md:text-2xl drop-shadow-md font-[family-name:var(--font-special-elite)]">
                    {actor.nombrePersonaje}
                  </div>
                  <div className="absolute -top-12 font-[family-name:var(--font-special-elite)] text-sm tracking-[0.2em] text-white animate-bounce bg-zinc-850/90 px-5 py-2 rounded-full border-2 border-zinc-700 shadow-xl">TURNO ACTUAL</div>
                </div>

                {/* VS or TARGET */}
                <div className="relative flex flex-col items-center mt-10 md:mt-0">
                  {(previewTarget || animState === 'DAMAGE') ? (
                    <div className={`relative flex flex-col items-center transition-all duration-300 ease-out ${animState === 'DAMAGE' ? 'translate-x-4 sepia contrast-[1.8] animate-[shake_0.4s_ease-in-out_infinite] scale-110 z-40' : ''}`}>
                      <div className={`w-40 h-56 md:w-56 md:h-72 rounded-xl overflow-hidden border-4 border-zinc-500 opacity-90 ${animState === 'DAMAGE' ? 'border-red-600 shadow-[0_0_50px_rgba(220,38,38,1)]' : ''}`}>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img
                          src={previewTarget ? previewTarget.image : portraitMap['DEFAULT']}
                          alt="Target"
                          className={`w-full h-full object-cover 
                            ${previewTarget?.charKey === 'AdaWong' ? 'object-center scale-[1.5]' :
                              previewTarget?.charKey === 'Onryo' ? 'object-bottom scale-[1.2] -translate-y-10' :
                                (previewTarget?.charKey === 'Animatronico' || previewTarget?.charKey === 'Animatrónico') ? 'object-[42%_30%] scale-[1.25]' :
                                  'object-top'}`}
                        />
                      </div>
                      <div className="mt-4 text-white bg-transparent font-normal tracking-wide text-xl md:text-2xl drop-shadow-md font-[family-name:var(--font-special-elite)]">
                        {previewTarget?.name || 'Objetivo'}
                      </div>
                      {animState === 'DAMAGE' && (
                        <div className="absolute inset-0 flex items-center justify-center animate-[scratch_0.5s_ease-out_forwards]">
                          <span className="text-8xl md:text-9xl text-red-600 font-black drop-shadow-[0_0_20px_rgba(220,38,38,1)] -rotate-[20deg] tracking-tighter">{"///"}</span>
                        </div>
                      )}
                    </div>
                  ) : (
                    <div className="w-24 h-24 md:w-32 md:h-32 rounded-full bg-zinc-950 border-4 border-red-900/60 flex items-center justify-center text-red-600 font-black text-4xl md:text-5xl shadow-[0_0_30px_rgba(220,38,38,0.4)] relative">
                      <div className="absolute inset-0 bg-red-600/20 blur-xl rounded-full animate-pulse"></div>
                      VS
                    </div>
                  )}
                </div>
              </div>

              {gameState.partidaTerminada && (
                <div className="absolute inset-0 bg-black/85 backdrop-blur-md z-50 flex items-center justify-center flex-col p-4">
                  <h2 className="text-5xl md:text-7xl font-normal text-zinc-100 tracking-[0.05em] mb-4 drop-shadow-[0_0_25px_rgba(255,255,255,0.35)] font-[family-name:var(--font-another-danger)] text-center">
                    PARTIDA FINALIZADA
                  </h2>
                  <h3 className={`text-2xl md:text-4xl font-normal tracking-[0.08em] font-[family-name:var(--font-horroroid)] text-center ${gameState.ganador === 'supervivientes' ? 'text-blue-500 drop-shadow-[0_0_12px_rgba(59,130,246,0.8)]' : 'text-red-600 drop-shadow-[0_0_12px_rgba(220,38,38,0.8)]'}`}>
                    GANAN LOS {gameState.ganador?.toUpperCase()}
                  </h3>
                  <button
                    onClick={() => router.push('/dashboard')}
                    className="mt-12 px-8 py-3.5 bg-stone-950 border border-stone-850 hover:border-red-900/60 text-stone-400 hover:text-white font-normal rounded-none tracking-widest transition-all shadow-xl font-[family-name:var(--font-special-elite)] text-sm active:scale-95 uppercase"
                  >
                    VOLVER AL DASHBOARD
                  </button>
                </div>
              )}
            </div>

            {/* Combat Log */}
            <div className="h-32 md:h-48 bg-black/85 border-t border-zinc-800 p-4 overflow-y-auto font-[family-name:var(--font-special-elite)] text-sm md:text-base leading-relaxed shadow-inner tracking-tight">
              {combatLogs.length <= 1 ? (
                <div className="text-zinc-500 flex flex-col gap-3 py-2">
                  <p className="font-bold text-zinc-400 uppercase tracking-widest text-sm mb-2 border-b border-zinc-900 pb-2">📋 GUÍA TÁCTICA DE LA PRUEBA</p>
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div>
                      <span className="text-[#fca5a5] font-bold">⚔ ATACAR:</span>
                      <p className="mt-1 text-xs text-zinc-500 leading-relaxed">
                        Arremete contra un oponente seleccionado para minar sus puntos de vida (HP) y acercarlo a su sacrificio o muerte.
                      </p>
                    </div>
                    <div>
                      <span className="text-[#a7f3d0] font-bold">🛡 DEFENDER:</span>
                      <p className="mt-1 text-xs text-zinc-500 leading-relaxed">
                        Adopta una postura defensiva que reduce a la mitad el daño que este personaje reciba durante el siguiente turno rival.
                      </p>
                    </div>
                    <div>
                      <span className="text-[#f3e8ff] font-bold">✨ USAR PERK:</span>
                      <p className="mt-1 text-xs text-zinc-500 leading-relaxed">
                        Desata una habilidad especial activa exclusiva del personaje actual para infligir efectos potentes. Tiene usos limitados.
                      </p>
                    </div>
                  </div>
                  <p className="text-[10px] text-zinc-600 mt-4 border-t border-zinc-900 pt-2 animate-pulse uppercase tracking-wider">
                    Realiza cualquier acción de combate para iniciar la Prueba y revelar el registro completo del combate...
                  </p>
                </div>
              ) : (
                <div className="space-y-1">
                  {combatLogs.map((log, i) => {
                    let colorClass = 'text-zinc-400';
                    if (log.includes('atacó') || log.includes('daño') || log.includes('CRÍTICO') || log.includes('MUERTO')) colorClass = 'text-red-400 font-bold';
                    else if (log.includes('Perk') || log.includes('usó')) colorClass = 'text-purple-400';
                    else if (log.includes('defensiva') || log.includes('bloqueo')) colorClass = 'text-blue-400';
                    else if (log.includes('turno')) colorClass = 'text-white font-black bg-white/10 px-2 py-0.5 rounded inline-block mt-2 mb-1';

                    return <p key={i} className={`${colorClass} transition-all`}>{log}</p>;
                  })}
                  <div ref={logsEndRef} />
                </div>
              )}
            </div>
          </main>

          {/* RIGHT SIDEBAR - KILLERS & CONTROLS */}
          <aside className="w-full md:w-80 bg-black/60 border-l border-zinc-800 flex flex-col shadow-2xl">
            {/* Killers List */}
            <div className="p-4 border-b border-zinc-800 hidden md:block">
              <h4 className="text-2xl font-normal text-red-600 tracking-widest mb-2 border-b border-red-900/50 pb-2 font-[family-name:var(--font-horroroid)]">ASESINOS</h4>
              <div className="space-y-2">
                {gameState.killers.slice(0, 3).map((k, idx) => {
                  const isDead = k.vidaActual <= 0;
                  const isMyTurn = !isSurviTurn && currentIdx === idx && !isDead;
                  return (
                    <CharacterCard key={`${k.nombrePersonaje}-${idx}`} char={k} isMyTurn={isMyTurn} isKiller={true} />
                  );
                })}
              </div>
            </div>

            {/* Controls */}
            <div className="flex-1 p-4 flex flex-col bg-zinc-950/80 font-[family-name:var(--font-special-elite)]">
              <div className="mb-4 flex justify-between items-start">
                <div>
                  <p className="text-xs text-zinc-500 tracking-widest mb-1 uppercase">Turno Actual</p>
                  <h3 className="text-2xl font-normal text-white truncate">{actor.nombrePersonaje}</h3>
                </div>
              </div>
              <div className="mb-4">
                <HealthBar value={actor.vidaActual} max={actor.vidaMax} />
                <p className="text-center text-[10px] font-normal text-zinc-400 mt-1 uppercase">{actor.vidaActual} / {actor.vidaMax} HP</p>
              </div>

              {!gameState.partidaTerminada && (
                <div className="flex-1 flex flex-col gap-2 justify-end">

                  {/* Action Panel */}
                  <div className="bg-zinc-900 border border-zinc-700 p-4 rounded-xl flex flex-col gap-3 shadow-[0_4px_20px_rgba(0,0,0,0.5)]">
                    <p className="text-xs font-black text-zinc-500 tracking-widest text-center border-b border-zinc-800 pb-2 mb-1">PANEL DE ACCIONES</p>
                    {gameState.modoJuego === 'automatico' ? (
                      <div className="py-6 flex flex-col items-center justify-center text-center">
                        <div className="w-12 h-12 bg-amber-500/20 border border-amber-500/50 rounded-full flex items-center justify-center animate-pulse mb-3">
                          <span className="text-2xl">🤖</span>
                        </div>
                        <p className="text-amber-500 font-black tracking-widest text-sm uppercase">Modo Automatico</p>
                        <p className="text-zinc-500 text-xs mt-1 max-w-[200px]">La Entidad está guiando las acciones de los combatientes.</p>
                      </div>
                    ) : activeMode === 'NONE' ? (
                      <>
                        <button onClick={() => setActiveMode('ATACAR')} disabled={isAnimating} className="w-full bg-[#3a0909] hover:bg-[#520f0f] text-[#fca5a5] border border-[#6b1414] font-normal py-3 rounded-none text-lg tracking-[0.15em] hover:text-white transition-all disabled:opacity-30">ATACAR</button>
                        <button onClick={() => sendAction('DEFENDER')} disabled={isAnimating} className="w-full bg-[#1b2b20] hover:bg-[#273d2e] text-[#a7f3d0] border border-[#2b4c37] font-normal py-3 rounded-none text-lg tracking-[0.15em] hover:text-white transition-all disabled:opacity-30">DEFENDER</button>
                        <button onClick={() => setActiveMode('PERK')} disabled={isAnimating || !actor.perks?.some(p => p.usos > 0)} className="w-full bg-[#27153a] hover:bg-[#391e55] text-[#f3e8ff] border border-[#4a2472] font-normal py-3 rounded-none text-lg tracking-[0.15em] hover:text-white transition-all disabled:opacity-30">USAR PERK</button>
                      </>
                    ) : activeMode === 'ATACAR' ? (
                      <div className="flex flex-col h-full font-[family-name:var(--font-special-elite)]">
                        <p className="text-sm text-zinc-400 mb-2 font-normal uppercase tracking-wider">Selecciona Objetivo:</p>
                        <div className="space-y-2 flex-1 overflow-y-auto pr-1 max-h-36">
                          {rivales.map((r, idx) => {
                            if (r.vidaActual <= 0) return null;
                            const fixedKey = r.nombrePersonaje.replace(/\s+/g, '');
                            return (
                              <button
                                key={`${r.nombrePersonaje}-${idx}`}
                                onMouseEnter={() => setPreviewTarget({ charKey: fixedKey, name: r.nombrePersonaje, image: portraitMap[fixedKey] || portraitMap['DEFAULT'] })}
                                onMouseLeave={() => setPreviewTarget(null)}
                                onClick={() => handleModeAction(idx)}
                                className="w-full text-left px-4 py-2 bg-stone-950 hover:bg-[#3a0909]/40 border border-stone-900 hover:border-[#6b1414] rounded-none text-sm font-normal transition-all text-[#fca5a5] hover:text-white"
                              >
                                {r.nombrePersonaje} <span className="float-right text-xs opacity-50">{r.vidaActual} HP</span>
                              </button>
                            );
                          })}
                        </div>
                        <button onClick={() => setActiveMode('NONE')} className="mt-3 w-full border border-stone-900 hover:bg-stone-900 py-1.5 rounded-none text-xs font-normal text-stone-500 transition-all uppercase tracking-widest">CANCELAR</button>
                      </div>
                    ) : activeMode === 'PERK' ? (
                      <div className="flex flex-col h-full font-[family-name:var(--font-special-elite)]">
                        <p className="text-sm text-zinc-400 mb-2 font-normal uppercase tracking-wider">Selecciona Perk:</p>
                        <div className="space-y-2 flex-1 overflow-y-auto pr-1 max-h-36">
                          {actor.perks?.map((p, idx) => {
                            if (p.usos <= 0) return null;
                            return (
                              <button
                                key={idx}
                                onClick={() => {
                                  sendAction('PERK', 0, idx);
                                }}
                                className="w-full text-left px-4 py-2 bg-stone-950 hover:bg-[#27153a]/40 border border-stone-900 hover:border-[#4a2472] rounded-none text-sm font-normal transition-all text-[#f3e8ff] hover:text-white"
                              >
                                {p.nombre} <span className="float-right text-xs opacity-50">{p.usos} usos</span>
                              </button>
                            );
                          })}
                        </div>
                        <button onClick={() => setActiveMode('NONE')} className="mt-3 w-full border border-stone-900 hover:bg-stone-900 py-1.5 rounded-none text-xs font-normal text-stone-500 transition-all uppercase tracking-widest">CANCELAR</button>
                      </div>
                    ) : null}
                  </div>

                  <div className="flex gap-2 mt-2 w-full">
                    <button
                      onClick={openSaveModal}
                      className="flex-1 bg-zinc-950 border-2 border-green-600 text-green-500 hover:text-green-400 font-black py-4 rounded-xl tracking-[0.1em] text-xs uppercase shadow-[0_0_12px_rgba(34,197,94,0.25)] hover:shadow-[0_0_20px_rgba(34,197,94,0.5)] hover:bg-green-950/30 transition-all active:scale-95 text-center"
                    >
                      GUARDAR
                    </button>
                    <button
                      onClick={() => router.push('/dashboard')}
                      className="flex-1 bg-zinc-950 border-2 border-red-600 text-red-500 hover:text-red-400 font-black py-4 rounded-xl tracking-[0.1em] text-xs uppercase shadow-[0_0_12px_rgba(220,38,38,0.25)] hover:shadow-[0_0_20px_rgba(220,38,38,0.5)] hover:bg-red-950/30 transition-all active:scale-95 text-center"
                    >
                      ABANDONAR
                    </button>
                  </div>
                </div>
              )}
            </div>
          </aside>
        </div>
      </div>

      {/* MODAL DE GUARDADO */}
      {isSaveModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/85 backdrop-blur-sm">
          <div className="bg-zinc-950 border border-zinc-800 rounded-2xl w-full max-w-2xl shadow-2xl overflow-hidden animate-in fade-in zoom-in duration-300">
            <div className="p-6 border-b border-zinc-800 flex justify-between items-center bg-black/40">
              <h3 className="text-2xl font-black text-transparent bg-clip-text bg-gradient-to-r from-green-400 to-green-200 tracking-widest uppercase">
                Sellar Progreso en la Niebla
              </h3>
              <button onClick={() => setIsSaveModalOpen(false)} className="text-zinc-500 hover:text-white transition-colors">
                <span className="text-2xl font-bold">×</span>
              </button>
            </div>

            <div className="p-6">
              <p className="text-zinc-400 text-sm mb-6">
                Elige una ranura del destino para resguardar tu estado actual de supervivencia o masacre. Si la ranura ya tiene una partida, se sobrescribirá.
              </p>

              {isLoadingSaves ? (
                <div className="flex flex-col items-center justify-center py-12">
                  <div className="w-12 h-12 border-4 border-green-500 border-t-transparent rounded-full animate-spin mb-4"></div>
                  <p className="text-zinc-400 font-bold tracking-widest uppercase animate-pulse">Consultando a la Entidad...</p>
                </div>
              ) : (
                <div className="flex flex-col gap-4">
                  {saves.map(save => (
                    <div
                      key={save.id}
                      onClick={() => handleSaveGame(save.id)}
                      className="group bg-zinc-900/60 hover:bg-green-950/20 border border-zinc-800 hover:border-green-600 p-5 rounded-xl flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 transition-all duration-300 hover:shadow-[0_0_20px_rgba(34,197,94,0.15)] cursor-pointer"
                    >
                      <div>
                        <div className="flex items-center gap-3 mb-1">
                          <p className="text-zinc-300 group-hover:text-green-400 font-black text-lg transition-colors">Ranura {save.id}</p>
                          <span className={`text-xs px-2 py-0.5 rounded uppercase font-bold tracking-wider ${save.vacia ? 'bg-zinc-800 text-zinc-500' : 'bg-red-900/40 text-red-400 border border-red-900/60'}`}>
                            {save.vacia ? 'Vacía' : 'Ocupada'}
                          </span>
                        </div>
                        {!save.vacia && (
                          <>
                            <p className="text-zinc-400 text-sm mb-1">
                              Modo: <span className="text-zinc-200">{save.modoJuego.toUpperCase()}</span> | Ronda: <span className="text-zinc-200">{save.ronda}</span>
                            </p>
                            <p className="text-zinc-500 text-xs flex gap-3">
                              <span>🏃 Vivos: <span className="text-blue-400">{save.survsVivos}</span></span>
                              <span>🔪 Vivos: <span className="text-red-400">{save.killersVivos}</span></span>
                            </p>
                          </>
                        )}
                      </div>

                      <button
                        onClick={(e) => {
                          e.stopPropagation();
                          handleSaveGame(save.id);
                        }}
                        className="px-6 py-2 rounded font-bold uppercase tracking-wider text-sm bg-green-700 hover:bg-green-600 text-white shadow-[0_0_10px_rgba(34,197,94,0.3)] hover:shadow-[0_0_20px_rgba(34,197,94,0.5)] transition-all group-hover:scale-105"
                      >
                        Sellar Aquí
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>
      )}

      <style dangerouslySetInnerHTML={{
        __html: `
        @keyframes shake {
          0%, 100% { transform: translateX(0) rotate(0deg); }
          25% { transform: translateX(-8px) rotate(-2deg); }
          50% { transform: translateX(8px) rotate(2deg); }
          75% { transform: translateX(-8px) rotate(-2deg); }
        }
        @keyframes scratch {
          0% { opacity: 0; transform: scale(1.5) rotate(-30deg); }
          20% { opacity: 1; transform: scale(1) rotate(-20deg); }
          100% { opacity: 0; transform: scale(1) rotate(-20deg) translateY(20px); }
        }
      `}} />
    </div>
  );
}
