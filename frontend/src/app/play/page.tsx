'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import toast, { Toaster } from 'react-hot-toast';
import { motion } from 'framer-motion';

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

interface Character {
  id: string;
  name: string;
  image: string;
}

export default function PlayPage() {
  const router = useRouter();
  const [availableSurvs, setAvailableSurvs] = useState<Character[]>([]);
  const [availableKillers, setAvailableKillers] = useState<Character[]>([]);

  const [selectedSurvs, setSelectedSurvs] = useState<string[]>([]);
  const [selectedKillers, setSelectedKillers] = useState<string[]>([]);

  const [userBando, setUserBando] = useState<'survivientes' | 'killers'>('survivientes');
  const [dificultad, setDificultad] = useState<'facil' | 'normal' | 'extremo'>('normal');

  const [isLoading, setIsLoading] = useState(true);
  const [isError, setIsError] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    const savedBando = localStorage.getItem('selectedBando') as 'survivientes' | 'killers';
    const savedDiff = localStorage.getItem('selectedDifficulty') as 'facil' | 'normal' | 'extremo';
    if (savedBando) setUserBando(savedBando);
    if (savedDiff) setDificultad(savedDiff);
  }, []);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 5000);

        const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1] || localStorage.getItem('jwt_token');
        const res = await fetch('/api/game/characters', {
          headers: token ? { 'Authorization': `Bearer ${token}` } : {},
          signal: controller.signal
        });
        clearTimeout(timeoutId);

        if (res.ok) {
          const data = await res.json();
          const mapToChar = (id: string): Character => ({
            id,
            name: id.replace(/([A-Z])/g, ' $1').trim(),
            image: portraitMap[id] || portraitMap["DEFAULT"]
          });

          setAvailableSurvs((data.supervivientes || []).map(mapToChar));
          setAvailableKillers((data.killers || []).map(mapToChar));
        } else {
          setIsError(true);
          toast.error("Error al conectar con la Entidad: " + res.status, { style: { background: '#7f1d1d', color: '#fff' } });
        }
      } catch (error) {
        setIsError(true);
        if (error instanceof Error && error.name === 'AbortError') {
          toast.error("Tiempo de espera agotado. El servidor no responde.", { style: { background: '#7f1d1d', color: '#fff' } });
        } else {
          toast.error("Error crítico de red. ¿Servidor apagado?", { style: { background: '#7f1d1d', color: '#fff' } });
        }
        console.error('Failed to connect to backend', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchCharacters();
  }, []);

  const toggleSurv = (id: string) => {
    setSelectedSurvs(prev => {
      if (prev.includes(id)) return prev.filter(x => x !== id);
      if (prev.length < 3) return [...prev, id];
      return prev;
    });
  };

  const toggleKiller = (id: string) => {
    setSelectedKillers(prev => {
      if (prev.includes(id)) return prev.filter(x => x !== id);
      if (prev.length < 3) return [...prev, id];
      return prev;
    });
  };

  const selectRandomCharacters = () => {
    if (availableSurvs.length < 3 || availableKillers.length < 3) {
      toast.error("No hay suficientes personajes disponibles en la Niebla.");
      return;
    }

    const shuffledSurvs = [...availableSurvs].sort(() => 0.5 - Math.random());
    const randomSurvIds = shuffledSurvs.slice(0, 3).map(s => s.id);

    const shuffledKillers = [...availableKillers].sort(() => 0.5 - Math.random());
    const randomKillerIds = shuffledKillers.slice(0, 3).map(k => k.id);

    setSelectedSurvs(randomSurvIds);
    setSelectedKillers(randomKillerIds);

    toast.success("La Entidad ha elegido tus campeones al azar...", {
      icon: '🎲',
      style: { background: '#18181b', color: '#fbbf24', border: '1px solid #d97706' }
    });
  };

  const handleStart = async (mode: 'manual' | 'automatico') => {
    if (selectedSurvs.length === 3 && selectedKillers.length === 3) {
      setIsSubmitting(true);
      try {
        localStorage.setItem('selectedSurvs', JSON.stringify(selectedSurvs));
        localStorage.setItem('selectedKillers', JSON.stringify(selectedKillers));
        localStorage.setItem('selectedMode', mode);
        localStorage.setItem('selectedBando', userBando);
        localStorage.setItem('selectedDifficulty', dificultad);
        await new Promise(r => setTimeout(r, 600));
        router.push('/trial');
      } catch (error) {
        toast.error("Error al iniciar partida");
        setIsSubmitting(false);
      }
    }
  };

  const isReady = selectedSurvs.length === 3 && selectedKillers.length === 3;

  return (
    <div className="min-h-screen bg-zinc-950 bg-[url('/maxresdefault.jpg')] bg-cover bg-center bg-fixed relative text-white selection:bg-red-600/30 font-sans">
      <div className="absolute inset-0 bg-black/85 z-0 backdrop-blur-[2px]"></div>
      <div className="absolute inset-0 bg-gradient-to-tr from-red-950/20 via-transparent to-blue-950/20 z-0 mix-blend-overlay animate-pulse"></div>

      <div className="relative z-10 p-3 md:p-5 w-full max-w-[1700px] mx-auto flex flex-col min-h-screen">

        {/* HEADER */}
        <header className="flex flex-col lg:flex-row justify-between items-center border-b border-red-900/20 pb-3 mb-4 gap-4 backdrop-blur-md bg-black/30 px-5 py-3 rounded-xl shadow-xl border-t border-zinc-800/20">
          <div className="text-center lg:text-left">
            <h1 className="text-4xl md:text-5xl font-normal text-white tracking-[0.05em] font-[family-name:var(--font-horroroid)] drop-shadow-[0_2px_10px_rgba(255,255,255,0.2)] uppercase">
              LA HOGUERA
            </h1>
            <p className="text-zinc-400 tracking-[0.1em] mt-1 text-2xs md:text-xs font-[family-name:var(--font-special-elite)]">
              elige tus personajes y adentrate a la niebla.
            </p>
          </div>

          <div className="flex flex-col sm:flex-row items-center gap-4 w-full lg:w-auto">
            <div className="flex gap-4 items-center bg-black/40 px-5 py-2.5 rounded-lg border border-zinc-850 shadow-inner">
              <div className="flex flex-col items-center">
                <span className="text-[10px] text-zinc-500 font-bold tracking-wider uppercase mb-1 font-[family-name:var(--font-special-elite)]">Supervivientes</span>
                <div className="flex gap-1">
                  {[0, 1, 2].map(i => (
                    <div key={i} className={`w-2.5 h-2.5 rounded-full ${i < selectedSurvs.length ? 'bg-blue-500 shadow-[0_0_10px_rgba(59,130,246,0.8)]' : 'bg-zinc-700'}`}></div>
                  ))}
                </div>
              </div>
              <div className="w-px h-6 bg-zinc-800"></div>
              <div className="flex flex-col items-center">
                <span className="text-[10px] text-zinc-500 font-bold tracking-wider uppercase mb-1 font-[family-name:var(--font-special-elite)]">Asesinos</span>
                <div className="flex gap-1">
                  {[0, 1, 2].map(i => (
                    <div key={i} className={`w-2.5 h-2.5 rounded-full ${i < selectedKillers.length ? 'bg-red-600 shadow-[0_0_10px_rgba(220,38,38,0.8)]' : 'bg-zinc-700'}`}></div>
                  ))}
                </div>
              </div>
            </div>

            <div className="flex gap-2 w-full sm:w-auto">
              <button
                onClick={selectRandomCharacters}
                className="group relative px-4 py-2.5 bg-amber-950/20 border border-amber-600/50 hover:bg-amber-950/40 text-amber-500 hover:text-amber-400 rounded-lg overflow-hidden font-[family-name:var(--font-special-elite)] tracking-wider uppercase text-xs transition-all duration-300 hover:shadow-[0_0_15px_rgba(245,158,11,0.2)] active:scale-95 flex-1 sm:flex-none flex items-center justify-center gap-2"
              >
                <span>🎲 Selección al Azar</span>
              </button>

              <button
                onClick={() => router.push('/dashboard')}
                className="group relative px-4 py-2.5 bg-zinc-900 hover:bg-zinc-850 text-zinc-400 hover:text-zinc-300 rounded-lg overflow-hidden font-[family-name:var(--font-special-elite)] tracking-wider uppercase text-xs border border-zinc-800 hover:border-zinc-700 transition-all duration-300 hover:shadow-[0_0_10px_rgba(255,255,255,0.05)] active:scale-95 flex-1 sm:flex-none"
              >
                <span className="relative flex items-center justify-center gap-1">
                  <span className="text-sm leading-none transition-transform group-hover:-translate-x-1">←</span> Volver
                </span>
              </button>
            </div>
          </div>
        </header>

        {/* MAIN ROSTER */}
        <main className="flex-1 flex flex-col">
          {isLoading ? (
            <div className="grid grid-cols-1 xl:grid-cols-[1fr_auto_1fr] gap-6 xl:gap-10 w-full animate-pulse mt-4 flex-1">
              <div className="bg-black/40 rounded-3xl p-8 border border-zinc-800">
                <div className="h-8 w-48 bg-zinc-800 rounded mb-8"></div>
                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                  {[1, 2, 3, 4, 5, 6, 7, 8].map(i => <div key={i} className="aspect-[3/4] bg-zinc-900 rounded-xl"></div>)}
                </div>
              </div>
              <div className="hidden xl:block w-10"></div>
              <div className="bg-black/40 rounded-3xl p-8 border border-zinc-800">
                <div className="h-8 w-48 bg-zinc-800 rounded mb-8"></div>
                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                  {[1, 2, 3, 4, 5, 6, 7, 8].map(i => <div key={i} className="aspect-[3/4] bg-zinc-900 rounded-xl"></div>)}
                </div>
              </div>
            </div>
          ) : isError ? (
            <div className="flex-1 flex items-center justify-center flex-col gap-4 text-center">
              <span className="text-6xl">☠️</span>
              <h2 className="text-3xl text-red-500 font-black tracking-widest">LA CONEXIÓN SE HA PERDIDO</h2>
              <p className="text-zinc-400 max-w-lg">La Entidad no puede establecer conexión con el servidor. Verifica que el backend esté corriendo.</p>
              <button onClick={() => window.location.reload()} className="mt-4 px-6 py-2 bg-red-900 text-white rounded hover:bg-red-800 font-bold uppercase transition-colors">Reintentar</button>
            </div>
          ) : (
            <>
            {/* PANEL DE CONFIGURACIÓN DE PARTIDA */}
            <motion.div
              initial={{ opacity: 0, y: -20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5 }}
              className="bg-black/60 border border-zinc-900/60 p-3 mb-3 rounded-xl flex flex-col md:flex-row justify-between items-center gap-4 shadow-lg backdrop-blur-md w-full"
            >
              {/* Bando Selector */}
              <div className="flex items-center gap-3 w-full md:w-auto">
                <span className="text-[11px] text-zinc-500 font-bold uppercase tracking-wider font-[family-name:var(--font-special-elite)]">
                  Bando que controlas:
                </span>
                <div className="flex bg-zinc-950 p-1 rounded-lg border border-zinc-900 w-full sm:w-auto">
                  <button
                    onClick={() => {
                      setUserBando('survivientes');
                      localStorage.setItem('selectedBando', 'survivientes');
                    }}
                    className={`flex-1 sm:flex-none px-4 py-1.5 rounded-md text-xs font-semibold tracking-wider transition-all duration-300 ${
                      userBando === 'survivientes'
                        ? 'bg-blue-900/35 text-blue-400 border border-blue-800 shadow-[0_0_15px_rgba(59,130,246,0.2)]'
                        : 'text-zinc-500 hover:text-zinc-300 border border-transparent'
                    }`}
                  >
                    🩸 Supervivientes
                  </button>
                  <button
                    onClick={() => {
                      setUserBando('killers');
                      localStorage.setItem('selectedBando', 'killers');
                    }}
                    className={`flex-1 sm:flex-none px-4 py-1.5 rounded-md text-xs font-semibold tracking-wider transition-all duration-300 ${
                      userBando === 'killers'
                        ? 'bg-red-950/35 text-red-500 border border-red-900 shadow-[0_0_15px_rgba(239,68,68,0.2)]'
                        : 'text-zinc-500 hover:text-zinc-300 border border-transparent'
                    }`}
                  >
                    💀 Asesinos
                  </button>
                </div>
              </div>

              {/* Dificultad Selector */}
              <div className="flex items-center gap-3 w-full md:w-auto">
                <span className="text-[11px] text-zinc-500 font-bold uppercase tracking-wider font-[family-name:var(--font-special-elite)]">
                  Dificultad de la Niebla:
                </span>
                <div className="flex bg-zinc-950 p-1 rounded-lg border border-zinc-900 w-full sm:w-auto">
                  <button
                    onClick={() => {
                      setDificultad('facil');
                      localStorage.setItem('selectedDifficulty', 'facil');
                    }}
                    className={`flex-grow sm:flex-none px-4 py-1.5 rounded-md text-xs font-semibold tracking-wider transition-all duration-300 ${
                      dificultad === 'facil'
                        ? 'bg-emerald-950/40 text-emerald-400 border border-emerald-800 shadow-[0_0_15px_rgba(52,211,153,0.15)]'
                        : 'text-zinc-500 hover:text-zinc-300 border border-transparent'
                    }`}
                  >
                    Fácil 🟢
                  </button>
                  <button
                    onClick={() => {
                      setDificultad('normal');
                      localStorage.setItem('selectedDifficulty', 'normal');
                    }}
                    className={`flex-grow sm:flex-none px-4 py-1.5 rounded-md text-xs font-semibold tracking-wider transition-all duration-300 ${
                      dificultad === 'normal'
                        ? 'bg-amber-950/40 text-amber-400 border border-amber-800 shadow-[0_0_15px_rgba(245,158,11,0.15)]'
                        : 'text-zinc-500 hover:text-zinc-300 border border-transparent'
                    }`}
                  >
                    Normal 🟡
                  </button>
                  <button
                    onClick={() => {
                      setDificultad('extremo');
                      localStorage.setItem('selectedDifficulty', 'extremo');
                    }}
                    className={`flex-grow sm:flex-none px-4 py-1.5 rounded-md text-xs font-semibold tracking-wider transition-all duration-300 ${
                      dificultad === 'extremo'
                        ? 'bg-red-950/40 text-red-500 border border-red-900 shadow-[0_0_20px_rgba(239,68,68,0.25)]'
                        : 'text-zinc-500 hover:text-zinc-300 border border-transparent'
                    }`}
                  >
                    Extremo 🔴
                  </button>
                </div>
              </div>
            </motion.div>

            <motion.div
              initial={{ opacity: 0, y: 30 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              className="grid grid-cols-1 xl:grid-cols-[1fr_auto_1fr] gap-4 xl:gap-6 items-start flex-1 w-full"
            >

              {/* SUPERVIVIENTES */}
              <div className="relative bg-black/40 backdrop-blur-sm border border-zinc-800/80 rounded-2xl p-4 shadow-xl overflow-hidden group/container">
                <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-blue-600/50 to-transparent"></div>

                <h3 className="text-2xl font-normal text-zinc-400 mb-4 flex items-center gap-3 border-b border-zinc-850 pb-2 tracking-widest uppercase font-[family-name:var(--font-horroroid)]">
                  Supervivientes
                </h3>

                <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 sm:gap-4">
                  {availableSurvs.map((s) => {
                    const isSelected = selectedSurvs.includes(s.id);
                    return (
                      <button
                        key={s.id}
                        onClick={() => toggleSurv(s.id)}
                        className={`relative aspect-[3.2/4] rounded-lg overflow-hidden cursor-pointer transition-all duration-300 group focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-zinc-900 ${isSelected
                          ? 'ring-2 ring-blue-500 ring-offset-2 ring-offset-zinc-950 shadow-[0_0_20px_rgba(59,130,246,0.4)] scale-[1.03] z-10'
                          : 'border border-zinc-850 hover:border-blue-500/40 hover:shadow-[0_0_15px_rgba(59,130,246,0.4)] hover:scale-[1.03] hover:z-10'
                          }`}
                      >
                        <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent z-10 opacity-90 transition-opacity duration-300 group-hover:opacity-40"></div>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img
                          src={s.image}
                          alt={s.name}
                          className={`object-cover w-full h-full transition-all duration-300 ease-out 
                            ${s.id === 'AdaWong' ? 'object-center scale-[1.5]' : 'object-top scale-100'} 
                            ${isSelected ? 'saturate-100 opacity-100 brightness-110' : 'saturate-0 opacity-70 group-hover:scale-105 group-hover:saturate-100 group-hover:opacity-100'}`}
                        />

                        <div className="absolute bottom-0 left-0 w-full p-2 sm:p-3 z-20 transform transition-transform duration-300 bg-gradient-to-t from-black via-black/90 to-transparent">
                          <p className={`text-xs sm:text-sm font-normal text-center uppercase tracking-wider font-[family-name:var(--font-special-elite)] ${isSelected ? 'text-blue-300 drop-shadow-[0_0_6px_rgba(59,130,246,0.8)]' : 'text-zinc-400 group-hover:text-white'
                            }`}>
                            {s.name}
                          </p>
                        </div>

                        {isSelected && (
                          <div className="absolute top-1.5 right-1.5 z-20 w-5.5 h-5.5 bg-blue-500 rounded-full flex items-center justify-center shadow-[0_0_8px_rgba(59,130,246,0.8)] animate-bounce">
                            <span className="text-white text-xs font-black">{selectedSurvs.indexOf(s.id) + 1}</span>
                          </div>
                        )}
                      </button>
                    )
                  })}
                </div>
              </div>

              {/* VS Divider & Random Button */}
              <div className="hidden xl:flex flex-col items-center justify-center self-stretch opacity-90 py-2 gap-4">
                <div className="w-[1px] h-full bg-gradient-to-b from-transparent via-zinc-800 to-transparent"></div>
                <div className="relative group">
                  <div className="absolute inset-0 bg-red-600 rounded-full blur-md opacity-35 animate-pulse"></div>
                  <div className="relative bg-zinc-950 border border-zinc-850 text-red-500 font-[family-name:var(--font-horroroid)] text-2xl w-14 h-14 flex items-center justify-center rounded-full shadow-[0_0_20px_rgba(220,38,38,0.3)] tracking-tighter">
                    VS
                  </div>
                </div>
                
                <button
                  onClick={selectRandomCharacters}
                  className="px-4 py-2 border border-amber-600/50 bg-amber-950/20 hover:bg-amber-950/40 text-amber-500 hover:text-amber-400 rounded-lg text-xs font-black tracking-widest uppercase transition-all duration-300 active:scale-95 flex items-center gap-2 font-[family-name:var(--font-special-elite)] shadow-[0_0_15px_rgba(245,158,11,0.15)] hover:shadow-[0_0_25px_rgba(245,158,11,0.3)]"
                >
                  🎲 Azar
                </button>
                <div className="w-[1px] h-full bg-gradient-to-t from-transparent via-zinc-800 to-transparent"></div>
              </div>

              {/* ASESINOS */}
              <div className="relative bg-black/40 backdrop-blur-sm border border-zinc-800/80 rounded-2xl p-4 shadow-xl overflow-hidden group/container">
                <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-red-600/50 to-transparent"></div>

                <h3 className="text-2xl font-normal text-zinc-400 mb-4 flex items-center gap-3 border-b border-zinc-850 pb-2 tracking-widest uppercase font-[family-name:var(--font-horroroid)]">
                  Asesinos
                </h3>

                <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 sm:gap-4">
                  {availableKillers.map((k) => {
                    const isSelected = selectedKillers.includes(k.id);
                    return (
                      <button
                        key={k.id}
                        onClick={() => toggleKiller(k.id)}
                        className={`relative aspect-[3.2/4] rounded-lg overflow-hidden cursor-pointer transition-all duration-300 group focus:outline-none focus:ring-2 focus:ring-red-600 focus:ring-offset-2 focus:ring-offset-zinc-900 ${isSelected
                          ? 'ring-2 ring-red-600 ring-offset-2 ring-offset-zinc-950 shadow-[0_0_20px_rgba(220,38,38,0.4)] scale-[1.03] z-10'
                          : 'border border-zinc-850 hover:border-red-500/40 hover:shadow-[0_0_15px_rgba(220,38,38,0.4)] hover:scale-[1.03] hover:z-10'
                          }`}
                      >
                        <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent z-10 opacity-90 transition-opacity duration-300 group-hover:opacity-40"></div>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img
                          src={k.image}
                          alt={k.name}
                          className={`object-cover w-full h-full transition-all duration-300 ease-out 
                            ${k.id === 'Onryo' ? 'object-bottom scale-[1.2] -translate-y-10' :
                              k.id === 'Animatronico' ? 'object-center scale-[1.3] -translate-y-6' :
                                'object-top scale-100'} 
                            ${isSelected ? 'saturate-100 opacity-100 brightness-110' : 'saturate-0 opacity-70 group-hover:scale-105 group-hover:saturate-100 group-hover:opacity-100'}`}
                        />

                        <div className="absolute bottom-0 left-0 w-full p-2 sm:p-3 z-20 transform transition-transform duration-300 bg-gradient-to-t from-black via-black/90 to-transparent">
                          <p className={`text-xs sm:text-sm font-normal text-center uppercase tracking-wider font-[family-name:var(--font-special-elite)] ${isSelected ? 'text-red-400 drop-shadow-[0_0_6px_rgba(220,38,38,0.8)]' : 'text-zinc-400 group-hover:text-white'
                            }`}>
                            {k.name}
                          </p>
                        </div>

                        {isSelected && (
                          <div className="absolute top-1.5 right-1.5 z-20 w-5.5 h-5.5 bg-red-600 rounded-full flex items-center justify-center shadow-[0_0_8px_rgba(220,38,38,0.8)] animate-bounce">
                            <span className="text-white text-xs font-black">{selectedKillers.indexOf(k.id) + 1}</span>
                          </div>
                        )}
                      </button>
                    )
                  })}
                </div>
              </div>
            </motion.div>
            {/* ACTION START BUTTONS */}
            <div className="mt-6 mb-2 max-w-4xl mx-auto w-full flex flex-col items-center">
              {isReady ? (
                <div className="flex flex-col sm:flex-row gap-4 w-full justify-center">
                  <button
                    onClick={() => handleStart('manual')}
                    disabled={isSubmitting}
                    className="flex-1 max-w-md relative px-8 py-3.5 bg-zinc-950/80 border-2 border-blue-500 hover:border-blue-400 text-blue-400 hover:text-blue-300 rounded-xl font-[family-name:var(--font-special-elite)] tracking-widest uppercase text-sm md:text-base shadow-[0_0_20px_rgba(59,130,246,0.2)] hover:shadow-[0_0_30px_rgba(59,130,246,0.55)] transition-all duration-300 active:scale-98 flex items-center justify-center gap-3"
                  >
                    <span className="text-xl">🎮</span>
                    <span>Jugar Modo Manual</span>
                  </button>

                  <button
                    onClick={() => handleStart('automatico')}
                    disabled={isSubmitting}
                    className="flex-1 max-w-md relative px-8 py-3.5 bg-zinc-950/80 border-2 border-amber-600 hover:border-amber-500 text-amber-500 hover:text-amber-400 rounded-xl font-[family-name:var(--font-special-elite)] tracking-widest uppercase text-sm md:text-base shadow-[0_0_20px_rgba(245,158,11,0.2)] hover:shadow-[0_0_30px_rgba(245,158,11,0.55)] transition-all duration-300 active:scale-98 flex items-center justify-center gap-3"
                  >
                    <span className="text-xl animate-pulse">🤖</span>
                    <span>Jugar Modo Automático</span>
                  </button>
                </div>
              ) : (
                <button
                  disabled
                  className="w-full max-w-2xl px-8 py-3.5 bg-zinc-900/50 text-zinc-500 border border-zinc-800 rounded-xl font-[family-name:var(--font-special-elite)] tracking-widest uppercase text-xs md:text-sm cursor-not-allowed backdrop-blur-sm"
                >
                  SELECCIONA 3 SUPERVIVIENTES Y 3 ASESINOS EN LA NIEBLA
                </button>
              )}
            </div>
            </>
          )}

          <style dangerouslySetInnerHTML={{
            __html: `
            @keyframes shimmer {
              100% { transform: translateX(100%); }
            }
          `}} />
        </main>
      </div>
    </div>
  );
}
