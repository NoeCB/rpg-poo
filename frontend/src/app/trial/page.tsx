'use client';

import { useState, useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';

const portraitMap: Record<string, string> = {
  // SUPERVIVIENTES
  "LeonKennedy": "/leon.jpg",
  "SteveHarrington": "/steve.jpg",
  "FengMin": "/fenming.jpg",
  "SableWard": "/sableward.jpg",
  "Mikaela": "/mikaelareid.jpg",
  "AdaWong": "/adawong.jpg",
  "LaraCroft": "/laracroft.jpg",
  "Nancy": "/nancy.jpg",
  // KILLERS
  "GhostFace": "/ghostface.jpg",
  "Legion": "/legion.jpg",
  "Onryo": "/onryo.jpg",
  "Animatronico": "/animatronico.jpg",
  "Ghoul": "/ghoul.jpg",
  "Chucky": "/chuckyf.jpg",
  "Wesker": "/wesker.jpg",
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
}

export default function TrialPage() {
  const router = useRouter();
  
  const [gameState, setGameState] = useState<GameState | null>(null);
  const [combatLogs, setCombatLogs] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  
  const [isSurviTurn, setIsSurviTurn] = useState(true);
  const [currentIdx, setCurrentIdx] = useState(0);
  
  const [activeMode, setActiveMode] = useState<'NONE' | 'ATACAR' | 'PERK'>('NONE');
  const [previewTarget, setPreviewTarget] = useState<{ charKey: string; name: string } | null>(null);
  const [isAnimating, setIsAnimating] = useState(false);
  const [animState, setAnimState] = useState<'IDLE' | 'ATTACK' | 'DAMAGE' | 'DEFEND'>('IDLE');

  const logsEndRef = useRef<HTMLDivElement>(null);

  // Original keys from localStorage to match portraits
  const [survKeys, setSurvKeys] = useState<string[]>([]);
  const [killerKeys, setKillerKeys] = useState<string[]>([]);

  useEffect(() => {
    // Scroll to bottom of logs
    logsEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [combatLogs]);

  useEffect(() => {
    const sKeys = JSON.parse(localStorage.getItem('selectedSurvs') || '[]');
    const kKeys = JSON.parse(localStorage.getItem('selectedKillers') || '[]');
    
    if (sKeys.length !== 3 || kKeys.length !== 3) {
      router.push('/play');
      return;
    }

    setSurvKeys(sKeys);
    setKillerKeys(kKeys);

    const initTrial = async () => {
      try {
        const baseUrl = typeof window !== 'undefined' ? `http://${window.location.hostname}:8080` : 'http://localhost:8080';
        const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
        const res = await fetch(`${baseUrl}/api/game/start-manual`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
          },
          body: JSON.stringify({ supervivientes: sKeys, killers: kKeys })
        });
        const data: GameState = await res.json();
        
        if (data && data.supervivientes) {
          setGameState(data);
          if (data.logs) setCombatLogs(data.logs);
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

    let equipoActual = isSurviTurn ? gameState.supervivientes : gameState.killers;
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
  const actor = equipoActual[currentIdx];
  // Safeguard just in case
  if (!actor) return null;

  const actorKey = isSurviTurn ? survKeys[currentIdx] : killerKeys[currentIdx];
  const actorImg = portraitMap[actorKey] || portraitMap['DEFAULT'];
  
  const pctVida = (actor.vidaActual / actor.vidaMax) * 100;
  const hpColor = pctVida > 50 ? 'bg-blue-500' : pctVida > 20 ? 'bg-orange-500' : 'bg-red-600';

  const sendAction = async (tipoAccion: string, objetivoIndex = -1, perkIndex = -1) => {
    if (isAnimating || gameState.partidaTerminada) return;
    
    setIsAnimating(true);
    setActiveMode('NONE');
    setPreviewTarget(null);

    const globalAtacanteIdx = isSurviTurn ? currentIdx : (gameState.supervivientes.length + currentIdx);
    const safeObjetivoIndex = objetivoIndex === -1 ? 0 : objetivoIndex;

    // Mini animation logic before API call
    if (tipoAccion === 'ATACAR' || tipoAccion === 'PERK') {
      setAnimState('ATTACK');
      await new Promise(r => setTimeout(r, 600));
      setAnimState('DAMAGE');
      await new Promise(r => setTimeout(r, 800));
      setAnimState('IDLE');
    } else if (tipoAccion === 'DEFENDER') {
      setAnimState('DEFEND');
      await new Promise(r => setTimeout(r, 800));
      setAnimState('IDLE');
    }

    try {
      const baseUrl = typeof window !== 'undefined' ? `http://${window.location.hostname}:8080` : 'http://localhost:8080';
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch(`${baseUrl}/api/game/action`, {
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

  const rivales = isSurviTurn ? gameState.killers : gameState.supervivientes;
  const rivalKeys = isSurviTurn ? killerKeys : survKeys;

  const handleModeAction = (targetIdx: number, perkIdx: number = -1) => {
    sendAction(activeMode, targetIdx, perkIdx);
  };

  return (
    <div className="min-h-screen bg-zinc-950 font-sans text-white overflow-hidden relative selection:bg-red-900/30">
      <div className="absolute inset-0 bg-[url('https://c4.wallpaperflare.com/wallpaper/528/773/953/dead-by-daylight-logo-4k-wallpaper-preview.jpg')] bg-cover bg-center opacity-10"></div>
      
      <div className="relative z-10 h-screen flex flex-col">
        {/* HEADER */}
        <header className="py-4 px-8 border-b border-zinc-800 bg-black/40 backdrop-blur-sm flex justify-between items-center shadow-lg">
          <h1 className="text-2xl font-black tracking-[0.2em] text-red-600 drop-shadow-[0_0_10px_rgba(220,38,38,0.5)]">LA PRUEBA</h1>
          <button onClick={() => router.push('/dashboard')} className="text-zinc-400 hover:text-white transition-colors text-sm font-bold tracking-wider uppercase border border-zinc-700 px-4 py-1 rounded">
            Abandonar
          </button>
        </header>

        <div className="flex-1 flex flex-col md:flex-row overflow-hidden">
          
          {/* LEFT SIDEBAR - SURVIVORS */}
          <aside className="w-full md:w-64 bg-black/60 border-r border-zinc-800 p-4 overflow-y-auto hidden md:block">
            <h4 className="text-blue-500 font-black tracking-widest mb-6 border-b border-blue-900/50 pb-2">SUPERVIVIENTES</h4>
            <div className="space-y-4">
              {gameState.supervivientes.map((s, idx) => {
                const isDead = s.vidaActual <= 0;
                return (
                  <div key={idx} className={`p-3 rounded border transition-all ${isDead ? 'border-zinc-800 bg-zinc-900/30 opacity-50' : 'border-blue-900/30 bg-blue-950/10'}`}>
                    <div className="flex justify-between items-center mb-1">
                      <span className={`text-sm font-bold ${isDead ? 'text-zinc-600 line-through' : 'text-blue-300'}`}>{s.nombrePersonaje}</span>
                      <span className={`text-xs font-black ${isDead ? 'text-zinc-600' : 'text-blue-200'}`}>{isDead ? 'MUERTO' : `${s.vidaActual} HP`}</span>
                    </div>
                    {!isDead && (
                      <div className="w-full bg-zinc-900 h-1.5 rounded-full overflow-hidden">
                        <div className="bg-blue-500 h-full transition-all duration-500" style={{ width: `${(s.vidaActual/s.vidaMax)*100}%` }}></div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          </aside>

          {/* CENTER BATTLE STAGE & LOGS */}
          <main className="flex-1 flex flex-col bg-zinc-950/50 backdrop-blur-sm relative">
            {/* Stage */}
            <div className="flex-1 relative flex items-center justify-center min-h-[300px]">
              
              <div className="flex flex-col md:flex-row items-center justify-center gap-10 md:gap-20 w-full px-4 md:px-10 h-full">
                {/* ACTOR (Attacker) */}
                <div className={`relative flex flex-col items-center transition-all duration-500 ease-in-out ${animState === 'ATTACK' ? 'translate-x-8 md:translate-x-24 scale-125 z-50' : ''} ${animState === 'DEFEND' ? 'scale-95 opacity-80 brightness-150 drop-shadow-[0_0_30px_rgba(59,130,246,1)]' : ''}`}>
                  <div className={`w-48 h-64 md:w-64 md:h-96 rounded-xl overflow-hidden border-4 ${isSurviTurn ? 'border-blue-500 shadow-[0_0_40px_rgba(59,130,246,0.6)]' : 'border-red-600 shadow-[0_0_40px_rgba(220,38,38,0.6)]'}`}>
                    {/* eslint-disable-next-line @next/next/no-img-element */}
                    <img src={actorImg} alt="Actor" className="w-full h-full object-cover object-top" />
                  </div>
                  <div className="mt-6 bg-black/90 px-6 py-2 rounded-lg border-2 border-zinc-700 text-lg md:text-xl font-black shadow-2xl tracking-wider">
                    {actor.nombrePersonaje}
                  </div>
                  <div className="absolute -top-12 font-black text-sm tracking-[0.2em] text-white animate-bounce bg-zinc-800/90 px-5 py-2 rounded-full border-2 border-zinc-500 shadow-xl">TURNO ACTUAL</div>
                </div>

                {/* VS or TARGET */}
                <div className="relative flex flex-col items-center mt-10 md:mt-0">
                  {(previewTarget || animState === 'DAMAGE') ? (
                    <div className={`relative flex flex-col items-center transition-all duration-300 ease-out ${animState === 'DAMAGE' ? 'translate-x-4 sepia contrast-[1.8] animate-[shake_0.4s_ease-in-out_infinite] scale-110 z-40' : ''}`}>
                      <div className={`w-48 h-64 md:w-64 md:h-96 rounded-xl overflow-hidden border-4 border-zinc-500 opacity-90 ${animState === 'DAMAGE' ? 'border-red-600 shadow-[0_0_50px_rgba(220,38,38,1)]' : ''}`}>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img src={previewTarget ? portraitMap[previewTarget.charKey] : portraitMap['DEFAULT']} alt="Target" className="w-full h-full object-cover object-top" />
                      </div>
                      <div className="mt-6 bg-black/90 px-6 py-2 rounded-lg border-2 border-zinc-700 text-lg md:text-xl font-black shadow-2xl tracking-wider">
                        {previewTarget?.name || 'Objetivo'}
                      </div>
                      {animState === 'DAMAGE' && (
                        <div className="absolute inset-0 flex items-center justify-center animate-[scratch_0.5s_ease-out_forwards]">
                          <span className="text-8xl md:text-9xl text-red-600 font-black drop-shadow-[0_0_20px_rgba(220,38,38,1)] -rotate-[20deg] tracking-tighter">///</span>
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
                <div className="absolute inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center flex-col">
                  <h2 className="text-6xl font-black text-transparent bg-clip-text bg-gradient-to-b from-white to-zinc-500 tracking-[0.3em] mb-4 drop-shadow-[0_0_20px_rgba(255,255,255,0.2)]">PARTIDA FINALIZADA</h2>
                  <h3 className={`text-3xl font-bold tracking-widest ${gameState.ganador === 'supervivientes' ? 'text-blue-500 drop-shadow-[0_0_15px_rgba(59,130,246,0.8)]' : 'text-red-600 drop-shadow-[0_0_15px_rgba(220,38,38,0.8)]'}`}>
                    GANAN LOS {gameState.ganador?.toUpperCase()}
                  </h3>
                  <button onClick={() => router.push('/dashboard')} className="mt-12 px-8 py-3 bg-zinc-800 hover:bg-zinc-700 text-white font-bold rounded tracking-widest transition-all shadow-lg border border-zinc-600">VOLVER AL DASHBOARD</button>
                </div>
              )}
            </div>

            {/* Combat Log */}
            <div className="h-48 md:h-64 bg-black/80 border-t border-zinc-800 p-4 overflow-y-auto font-mono text-sm leading-relaxed shadow-inner">
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
            </div>
          </main>

          {/* RIGHT SIDEBAR - KILLERS & CONTROLS */}
          <aside className="w-full md:w-80 bg-black/60 border-l border-zinc-800 flex flex-col shadow-2xl">
            {/* Killers List */}
            <div className="p-4 border-b border-zinc-800 hidden md:block">
              <h4 className="text-red-600 font-black tracking-widest mb-4 border-b border-red-900/50 pb-2">ASESINOS</h4>
              <div className="space-y-3">
                {gameState.killers.map((k, idx) => {
                  const isDead = k.vidaActual <= 0;
                  return (
                    <div key={idx} className={`p-2 rounded border transition-all ${isDead ? 'border-zinc-800 bg-zinc-900/30 opacity-50' : 'border-red-900/30 bg-red-950/10'}`}>
                      <div className="flex justify-between items-center mb-1">
                        <span className={`text-xs font-bold ${isDead ? 'text-zinc-600 line-through' : 'text-red-300'}`}>{k.nombrePersonaje}</span>
                        <span className={`text-[10px] font-black ${isDead ? 'text-zinc-600' : 'text-red-200'}`}>{isDead ? 'MUERTO' : `${k.vidaActual} HP`}</span>
                      </div>
                      {!isDead && (
                        <div className="w-full bg-zinc-900 h-1 rounded-full overflow-hidden">
                          <div className="bg-red-600 h-full transition-all duration-500" style={{ width: `${(k.vidaActual/k.vidaMax)*100}%` }}></div>
                        </div>
                      )}
                    </div>
                  );
                })}
              </div>
            </div>

            {/* Controls */}
            <div className="flex-1 p-6 flex flex-col bg-zinc-950/80">
              <div className="mb-6">
                <p className="text-xs text-zinc-500 tracking-widest mb-1 uppercase">Turno Actual</p>
                <h3 className="text-2xl font-black text-white truncate">{actor.nombrePersonaje}</h3>
                <div className="mt-3 bg-zinc-900 h-3 rounded-full overflow-hidden border border-zinc-800 relative shadow-inner">
                  <div className={`h-full transition-all duration-500 ${hpColor}`} style={{ width: `${pctVida}%` }}></div>
                  <span className="absolute inset-0 flex items-center justify-center text-[10px] font-black text-white drop-shadow-md">{actor.vidaActual} / {actor.vidaMax}</span>
                </div>
              </div>

              {!gameState.partidaTerminada && (
                <div className="flex-1 flex flex-col gap-3 justify-end">
                  {activeMode === 'NONE' ? (
                    <>
                      <button onClick={() => setActiveMode('ATACAR')} disabled={isAnimating} className="w-full bg-red-700 hover:bg-red-600 text-white font-bold py-3 rounded tracking-wider shadow-[0_0_15px_rgba(220,38,38,0.2)] hover:shadow-[0_0_20px_rgba(220,38,38,0.4)] transition-all disabled:opacity-50">ATACAR</button>
                      <button onClick={() => sendAction('DEFENDER')} disabled={isAnimating} className="w-full bg-blue-800 hover:bg-blue-700 text-white font-bold py-3 rounded tracking-wider transition-all disabled:opacity-50">DEFENDER</button>
                      <button onClick={() => setActiveMode('PERK')} disabled={isAnimating || !actor.perks?.some(p => p.usos > 0)} className="w-full bg-purple-900 hover:bg-purple-800 text-white font-bold py-3 rounded tracking-wider transition-all disabled:opacity-50">USAR PERK</button>
                    </>
                  ) : activeMode === 'ATACAR' ? (
                    <div className="flex flex-col h-full">
                      <p className="text-sm text-zinc-400 mb-3 font-bold">Selecciona Objetivo:</p>
                      <div className="space-y-2 flex-1 overflow-y-auto pr-1">
                        {rivales.map((r, idx) => {
                          if (r.vidaActual <= 0) return null;
                          return (
                            <button 
                              key={idx}
                              onMouseEnter={() => setPreviewTarget({ charKey: rivalKeys[idx], name: r.nombrePersonaje })}
                              onMouseLeave={() => setPreviewTarget(null)}
                              onClick={() => handleModeAction(idx)}
                              className="w-full text-left px-4 py-3 bg-zinc-900 hover:bg-zinc-800 border border-zinc-700 rounded text-sm font-bold transition-all text-zinc-200 hover:text-white"
                            >
                              {r.nombrePersonaje} <span className="float-right text-xs opacity-50">{r.vidaActual} HP</span>
                            </button>
                          );
                        })}
                      </div>
                      <button onClick={() => setActiveMode('NONE')} className="mt-4 w-full border border-zinc-700 hover:bg-zinc-800 py-2 rounded text-sm font-bold text-zinc-400 transition-all">CANCELAR</button>
                    </div>
                  ) : activeMode === 'PERK' ? (
                    <div className="flex flex-col h-full">
                      <p className="text-sm text-zinc-400 mb-3 font-bold">Selecciona Perk:</p>
                      <div className="space-y-2 flex-1 overflow-y-auto pr-1">
                        {actor.perks?.map((p, idx) => {
                          if (p.usos <= 0) return null;
                          return (
                            <button 
                              key={idx}
                              onClick={() => {
                                // Default to target 0 if backend doesn't care, or extend here if needed
                                sendAction('PERK', 0, idx); 
                              }}
                              className="w-full text-left px-4 py-3 bg-purple-950/50 hover:bg-purple-900 border border-purple-900/50 rounded text-sm font-bold transition-all text-purple-200"
                            >
                              {p.nombre} <span className="float-right text-xs opacity-50">{p.usos} usos</span>
                            </button>
                          );
                        })}
                      </div>
                      <button onClick={() => setActiveMode('NONE')} className="mt-4 w-full border border-zinc-700 hover:bg-zinc-800 py-2 rounded text-sm font-bold text-zinc-400 transition-all">CANCELAR</button>
                    </div>
                  ) : null}
                </div>
              )}
            </div>
          </aside>
        </div>
      </div>

      <style dangerouslySetInnerHTML={{__html: `
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
