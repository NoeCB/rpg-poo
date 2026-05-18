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
  const [gameMode, setGameMode] = useState<'manual' | 'automatico'>('manual');

  const [isLoading, setIsLoading] = useState(true);
  const [isError, setIsError] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 5000);

        const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
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
      } catch (error: any) {
        setIsError(true);
        if (error.name === 'AbortError') {
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

  const handleStart = async () => {
    if (selectedSurvs.length === 3 && selectedKillers.length === 3) {
      setIsSubmitting(true);
      try {
        localStorage.setItem('selectedSurvs', JSON.stringify(selectedSurvs));
        localStorage.setItem('selectedKillers', JSON.stringify(selectedKillers));
        localStorage.setItem('selectedMode', gameMode);
        await new Promise(r => setTimeout(r, 800));
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
      <div className="absolute inset-0 bg-black/85 z-0 backdrop-blur-[3px]"></div>
      <div className="absolute inset-0 bg-gradient-to-tr from-red-950/20 via-transparent to-blue-950/20 z-0 mix-blend-overlay animate-pulse"></div>

      <div className="relative z-10 p-4 md:p-8 w-full max-w-[1800px] mx-auto flex flex-col min-h-screen">
        
        {/* HEADER */}
        <header className="flex flex-col lg:flex-row justify-between items-center border-b border-red-900/30 pb-6 mb-10 gap-6 backdrop-blur-md bg-black/20 p-6 rounded-2xl shadow-2xl border-t border-zinc-800/50">
          <div className="text-center lg:text-left">
            <h1 className="text-4xl md:text-5xl font-black text-transparent bg-clip-text bg-gradient-to-r from-zinc-100 to-zinc-400 tracking-widest drop-shadow-[0_2px_10px_rgba(0,0,0,0.8)] uppercase">
              Preparación
            </h1>
            <p className="text-red-500 font-bold tracking-[0.3em] mt-2 text-xs md:text-sm uppercase drop-shadow-[0_0_8px_rgba(220,38,38,0.6)]">
              La Hoguera (The Campfire)
            </p>
          </div>
          
          <div className="flex flex-col sm:flex-row items-center gap-6 w-full lg:w-auto">
            <div className="flex gap-4 items-center bg-black/40 px-6 py-3 rounded-xl border border-zinc-800/80 shadow-inner">
              <div className="flex flex-col items-center">
                <span className="text-xs text-zinc-500 font-bold tracking-wider uppercase mb-1">Supervivientes</span>
                <div className="flex gap-1">
                  {[0, 1, 2].map(i => (
                    <div key={i} className={`w-3 h-3 rounded-full ${i < selectedSurvs.length ? 'bg-blue-500 shadow-[0_0_10px_rgba(59,130,246,0.8)]' : 'bg-zinc-700'}`}></div>
                  ))}
                </div>
              </div>
              <div className="w-px h-8 bg-zinc-800"></div>
              <div className="flex flex-col items-center">
                <span className="text-xs text-zinc-500 font-bold tracking-wider uppercase mb-1">Asesinos</span>
                <div className="flex gap-1">
                  {[0, 1, 2].map(i => (
                    <div key={i} className={`w-3 h-3 rounded-full ${i < selectedKillers.length ? 'bg-red-600 shadow-[0_0_10px_rgba(220,38,38,0.8)]' : 'bg-zinc-700'}`}></div>
                  ))}
                </div>
              </div>
            </div>
            
            <button 
              onClick={() => router.push('/dashboard')} 
              className="group relative px-6 py-3 bg-zinc-900 text-zinc-300 rounded-xl overflow-hidden font-bold tracking-wider uppercase text-sm border border-zinc-700 hover:border-zinc-500 transition-all duration-300 hover:shadow-[0_0_20px_rgba(255,255,255,0.1)] active:scale-95 w-full sm:w-auto"
            >
              <div className="absolute inset-0 w-0 bg-white/5 transition-all duration-[250ms] ease-out group-hover:w-full"></div>
              <span className="relative flex items-center justify-center gap-2">
                <span className="text-lg leading-none transition-transform group-hover:-translate-x-1">←</span> Volver
              </span>
            </button>
          </div>
        </header>

        {/* MAIN ROSTER */}
        <main className="flex-1 flex flex-col">
          {isLoading ? (
            <div className="grid grid-cols-1 xl:grid-cols-[1fr_auto_1fr] gap-6 xl:gap-10 w-full animate-pulse mt-4 flex-1">
               <div className="bg-black/40 rounded-3xl p-8 border border-zinc-800">
                  <div className="h-8 w-48 bg-zinc-800 rounded mb-8"></div>
                  <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                     {[1,2,3,4,5,6,7,8].map(i => <div key={i} className="aspect-[3/4] bg-zinc-900 rounded-xl"></div>)}
                  </div>
               </div>
               <div className="hidden xl:block w-10"></div>
               <div className="bg-black/40 rounded-3xl p-8 border border-zinc-800">
                  <div className="h-8 w-48 bg-zinc-800 rounded mb-8"></div>
                  <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                     {[1,2,3,4,5,6,7,8].map(i => <div key={i} className="aspect-[3/4] bg-zinc-900 rounded-xl"></div>)}
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
              <motion.div 
              initial={{ opacity: 0, y: 30 }} 
              animate={{ opacity: 1, y: 0 }} 
              transition={{ duration: 0.6 }}
              className="grid grid-cols-1 xl:grid-cols-[1fr_auto_1fr] gap-6 xl:gap-10 items-start flex-1 w-full"
            >
              
              {/* SUPERVIVIENTES */}
              <div className="relative bg-black/40 backdrop-blur-sm border border-zinc-800/80 rounded-3xl p-6 sm:p-8 shadow-2xl overflow-hidden group/container">
                <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-blue-600/50 to-transparent"></div>
                
                <h3 className="text-2xl font-black text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-blue-200 mb-8 flex items-center gap-3 border-b border-blue-900/30 pb-4 tracking-widest uppercase">
                  <span className="text-3xl drop-shadow-[0_0_15px_rgba(59,130,246,0.6)]">🏃</span> Supervivientes
                </h3>
                
                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 sm:gap-5">
                  {availableSurvs.map((s) => {
                    const isSelected = selectedSurvs.includes(s.id);
                    return (
                      <button 
                        key={s.id}
                        onClick={() => toggleSurv(s.id)}
                        className={`relative aspect-[3/4] rounded-xl overflow-hidden cursor-pointer transition-all duration-500 group focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-zinc-900 ${
                          isSelected 
                            ? 'ring-2 ring-blue-500 ring-offset-4 ring-offset-zinc-950 shadow-[0_0_30px_rgba(59,130,246,0.5)] scale-105 z-10' 
                            : 'border border-zinc-800 hover:border-blue-500/50 hover:shadow-[0_0_20px_rgba(59,130,246,0.6)] hover:scale-105 hover:z-10'
                        }`}
                      >
                        <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent z-10 opacity-90 transition-opacity duration-300 group-hover:opacity-40"></div>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img 
                          src={s.image} 
                          alt={s.name} 
                          className={`object-cover w-full h-full transition-all duration-500 ease-out 
                            ${s.id === 'AdaWong' ? 'object-center scale-[1.5]' : 'object-top scale-100'} 
                            ${isSelected ? 'saturate-100 opacity-100 brightness-110' : 'saturate-0 opacity-70 group-hover:scale-110 group-hover:saturate-100 group-hover:opacity-100'}`} 
                        />
                        
                        <div className="absolute bottom-0 left-0 w-full p-3 sm:p-5 z-20 transform transition-transform duration-300 bg-gradient-to-t from-black via-black/80 to-transparent">
                          <p className={`text-sm md:text-base lg:text-lg font-black text-center uppercase tracking-wider ${
                            isSelected ? 'text-blue-300 drop-shadow-[0_0_8px_rgba(59,130,246,1)]' : 'text-zinc-300 group-hover:text-white'
                          }`}>
                            {s.name}
                          </p>
                        </div>

                        {isSelected && (
                          <div className="absolute top-2 right-2 sm:top-3 sm:right-3 z-20 w-5 h-5 sm:w-6 sm:h-6 bg-blue-500 rounded-full flex items-center justify-center shadow-[0_0_10px_rgba(59,130,246,0.8)] animate-bounce">
                            <span className="text-white text-xs font-black">{selectedSurvs.indexOf(s.id) + 1}</span>
                          </div>
                        )}
                      </button>
                    )
                  })}
                </div>
              </div>

              {/* VS Divider */}
              <div className="hidden xl:flex flex-col items-center justify-center self-stretch opacity-80 py-10">
                <div className="w-[2px] h-full bg-gradient-to-b from-transparent via-red-900/50 to-transparent"></div>
                <div className="my-6 relative group">
                  <div className="absolute inset-0 bg-red-600 rounded-full blur-md opacity-40 group-hover:opacity-70 transition-opacity duration-500 animate-pulse"></div>
                  <div className="relative bg-zinc-950 border-2 border-red-900 text-transparent bg-clip-text bg-gradient-to-br from-red-400 to-red-700 font-black text-4xl w-20 h-20 flex items-center justify-center rounded-full shadow-[0_0_30px_rgba(220,38,38,0.4)] tracking-tighter">
                    VS
                  </div>
                </div>
                <div className="w-[2px] h-full bg-gradient-to-t from-transparent via-red-900/50 to-transparent"></div>
              </div>

              {/* ASESINOS */}
              <div className="relative bg-black/40 backdrop-blur-sm border border-zinc-800/80 rounded-3xl p-6 sm:p-8 shadow-2xl overflow-hidden group/container">
                <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-red-600/50 to-transparent"></div>
                
                <h3 className="text-2xl font-black text-transparent bg-clip-text bg-gradient-to-r from-red-500 to-red-300 mb-8 flex items-center gap-3 border-b border-red-900/30 pb-4 tracking-widest uppercase">
                  <span className="text-3xl drop-shadow-[0_0_15px_rgba(220,38,38,0.6)]">🔪</span> Asesinos
                </h3>
                
                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 sm:gap-5">
                  {availableKillers.map((k) => {
                    const isSelected = selectedKillers.includes(k.id);
                    return (
                      <button 
                        key={k.id}
                        onClick={() => toggleKiller(k.id)}
                        className={`relative aspect-[3/4] rounded-xl overflow-hidden cursor-pointer transition-all duration-500 group focus:outline-none focus:ring-2 focus:ring-red-600 focus:ring-offset-2 focus:ring-offset-zinc-900 ${
                          isSelected 
                            ? 'ring-2 ring-red-600 ring-offset-4 ring-offset-zinc-950 shadow-[0_0_30px_rgba(220,38,38,0.5)] scale-105 z-10' 
                            : 'border border-zinc-800 hover:border-red-500/50 hover:shadow-[0_0_20px_rgba(220,38,38,0.6)] hover:scale-105 hover:z-10'
                        }`}
                      >
                        <div className="absolute inset-0 bg-gradient-to-t from-black via-black/40 to-transparent z-10 opacity-90 transition-opacity duration-300 group-hover:opacity-40"></div>
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img 
                          src={k.image} 
                          alt={k.name} 
                          className={`object-cover w-full h-full transition-all duration-500 ease-out 
                            ${k.id === 'Onryo' ? 'object-bottom scale-[1.2] -translate-y-10' : 
                              k.id === 'Animatronico' ? 'object-center scale-[1.3] -translate-y-6' : 
                              'object-top scale-100'} 
                            ${isSelected ? 'saturate-100 opacity-100 brightness-110' : 'saturate-0 opacity-70 group-hover:scale-110 group-hover:saturate-100 group-hover:opacity-100'}`} 
                        />
                        
                        <div className="absolute bottom-0 left-0 w-full p-3 sm:p-5 z-20 transform transition-transform duration-300 bg-gradient-to-t from-black via-black/80 to-transparent">
                          <p className={`text-sm md:text-base lg:text-lg font-black text-center uppercase tracking-wider ${
                            isSelected ? 'text-red-400 drop-shadow-[0_0_8px_rgba(220,38,38,1)]' : 'text-zinc-300 group-hover:text-white'
                          }`}>
                            {k.name}
                          </p>
                        </div>

                        {isSelected && (
                          <div className="absolute top-2 right-2 sm:top-3 sm:right-3 z-20 w-5 h-5 sm:w-6 sm:h-6 bg-red-600 rounded-full flex items-center justify-center shadow-[0_0_10px_rgba(220,38,38,0.8)] animate-bounce">
                            <span className="text-white text-xs font-black">{selectedKillers.indexOf(k.id) + 1}</span>
                          </div>
                        )}
                      </button>
                    )
                  })}
                </div>
              </div>
            </motion.div>
          {/* GAME MODE SELECTOR */}
          <div className="mt-12 max-w-2xl mx-auto w-full">
            <h4 className="text-zinc-500 font-bold tracking-widest text-xs uppercase text-center mb-4">Selecciona el Modo de Juego</h4>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {/* MODO MANUAL */}
              <button 
                onClick={() => setGameMode('manual')}
                className={`p-4 rounded-xl border text-left transition-all duration-300 relative overflow-hidden group ${
                  gameMode === 'manual'
                  ? 'bg-blue-950/20 border-blue-500 shadow-[0_0_20px_rgba(59,130,246,0.2)]'
                  : 'bg-black/40 border-zinc-800 hover:border-zinc-700'
                }`}
              >
                <div className="flex items-center gap-3 mb-1">
                  <span className={`text-xl ${gameMode === 'manual' ? 'text-blue-400' : 'text-zinc-500'}`}>🎮</span>
                  <span className={`font-black tracking-wider uppercase text-sm ${gameMode === 'manual' ? 'text-white' : 'text-zinc-400'}`}>Modo Manual</span>
                </div>
                <p className="text-zinc-500 text-xs leading-relaxed">
                  Control absoluto sobre las acciones, perks y posturas defensivas de cada combatiente.
                </p>
                {gameMode === 'manual' && (
                  <div className="absolute top-2 right-2 w-2 h-2 rounded-full bg-blue-500 shadow-[0_0_8px_rgba(59,130,246,1)]"></div>
                )}
              </button>

              {/* MODO AUTOMÁTICO */}
              <button 
                onClick={() => setGameMode('automatico')}
                className={`p-4 rounded-xl border text-left transition-all duration-300 relative overflow-hidden group ${
                  gameMode === 'automatico'
                  ? 'bg-amber-950/20 border-amber-500 shadow-[0_0_20px_rgba(245,158,11,0.2)]'
                  : 'bg-black/40 border-zinc-800 hover:border-zinc-700'
                }`}
              >
                <div className="flex items-center gap-3 mb-1">
                  <span className={`text-xl ${gameMode === 'automatico' ? 'text-amber-400 animate-pulse' : 'text-zinc-500'}`}>🤖</span>
                  <span className={`font-black tracking-wider uppercase text-sm ${gameMode === 'automatico' ? 'text-white' : 'text-zinc-400'}`}>Modo Automático</span>
                </div>
                <p className="text-zinc-500 text-xs leading-relaxed">
                  La IA decide las mejores jugadas de supervivientes y asesinos de forma autónoma.
                </p>
                {gameMode === 'automatico' && (
                  <div className="absolute top-2 right-2 w-2 h-2 rounded-full bg-amber-500 shadow-[0_0_8px_rgba(245,158,11,1)]"></div>
                )}
              </button>
            </div>
          </div>
            </>
          )}

          {/* ACTION BUTTON */}
          <div className="mt-12 mb-6 flex justify-center relative">
            {isReady && (
              <div className="absolute inset-0 bg-white blur-[60px] opacity-5 rounded-full animate-pulse"></div>
            )}
            
            <button 
              onClick={handleStart}
              disabled={!isReady}
              className={`relative px-16 py-4 rounded-sm font-bold text-xl md:text-2xl tracking-[0.4em] uppercase overflow-hidden transition-all duration-700 ${
                isReady 
                ? 'bg-gradient-to-b from-zinc-700 via-zinc-800 to-zinc-900 text-zinc-300 shadow-[0_0_50px_rgba(0,0,0,0.8)] cursor-pointer hover:brightness-125 group border-y border-zinc-500/30' 
                : 'bg-zinc-900/50 text-zinc-600 border border-zinc-800 cursor-not-allowed backdrop-blur-sm'
              }`}
            >
              {/* Weathered Texture Overlay */}
              {isReady && (
                <div className="absolute inset-0 opacity-30 pointer-events-none mix-blend-overlay bg-[url('https://www.transparenttextures.com/patterns/asfalt-dark.png')]"></div>
              )}
              
              <span className="relative z-10 flex items-center justify-center drop-shadow-[0_2px_4px_rgba(0,0,0,0.8)]">
                {isReady ? 'Entrar a la Niebla' : 'Selecciona 3 y 3'}
              </span>

              {/* Subtle metallic shine on hover */}
              {isReady && (
                <div className="absolute inset-0 w-full h-full bg-gradient-to-r from-transparent via-white/5 to-transparent -translate-x-full group-hover:animate-[shimmer_2s_infinite]"></div>
              )}
            </button>
          </div>
          
          <style dangerouslySetInnerHTML={{__html: `
            @keyframes shimmer {
              100% { transform: translateX(100%); }
            }
          `}} />
        </main>
      </div>
    </div>
  );
}
