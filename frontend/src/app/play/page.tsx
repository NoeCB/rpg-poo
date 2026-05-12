'use client';

import { useState, useEffect } from 'react';
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

  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Fetch characters from backend via Next.js proxy
    const fetchCharacters = async () => {
      try {
        // Fetch characters from backend via proxy
        const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
        const res = await fetch('/api/game/characters', {
          headers: token ? { 'Authorization': `Bearer ${token}` } : {}
        });
        
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
          console.error('Error fetching characters');
        }
      } catch (error) {
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

  const handleStart = () => {
    if (selectedSurvs.length === 3 && selectedKillers.length === 3) {
      localStorage.setItem('selectedSurvs', JSON.stringify(selectedSurvs));
      localStorage.setItem('selectedKillers', JSON.stringify(selectedKillers));
      router.push('/trial');
    }
  };

  const isReady = selectedSurvs.length === 3 && selectedKillers.length === 3;

  return (
    <div className="min-h-screen bg-zinc-950 bg-[url('https://c4.wallpaperflare.com/wallpaper/132/511/309/dead-by-daylight-dark-wood-video-games-wallpaper-preview.jpg')] bg-cover bg-center bg-fixed relative text-white selection:bg-red-600/30 font-sans">
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
            <div className="flex-1 flex items-center justify-center">
              <div className="text-center">
                <div className="w-16 h-16 border-4 border-red-600 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
                <p className="text-zinc-400 font-bold tracking-widest uppercase animate-pulse">Contactando a la Entidad...</p>
              </div>
            </div>
          ) : (
            <div className="grid grid-cols-1 xl:grid-cols-[1fr_auto_1fr] gap-6 xl:gap-10 items-start flex-1 w-full">
              
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
                        <img src={s.image} alt={s.name} className={`object-cover object-top w-full h-full transition-all duration-500 ease-out ${isSelected ? 'scale-110 saturate-100' : 'saturate-0 opacity-70 group-hover:scale-110 group-hover:saturate-100 group-hover:opacity-100'}`} />
                        
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
                        <img src={k.image} alt={k.name} className={`object-cover object-top w-full h-full transition-all duration-500 ease-out ${isSelected ? 'scale-110 saturate-100' : 'saturate-0 opacity-70 group-hover:scale-110 group-hover:saturate-100 group-hover:opacity-100'}`} />
                        
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
            </div>
          )}

          {/* ACTION BUTTON */}
          <div className="mt-12 mb-6 flex justify-center relative">
            {isReady && (
              <div className="absolute inset-0 bg-red-600 blur-[50px] opacity-20 rounded-full animate-pulse"></div>
            )}
            
            <button 
              onClick={handleStart}
              disabled={!isReady}
              className={`relative px-12 py-5 rounded-2xl font-black text-xl md:text-2xl tracking-[0.2em] uppercase overflow-hidden transition-all duration-500 ${
                isReady 
                ? 'bg-gradient-to-b from-red-600 to-red-900 text-white shadow-[0_10px_40px_rgba(220,38,38,0.5)] cursor-pointer hover:-translate-y-2 hover:shadow-[0_15px_50px_rgba(220,38,38,0.7)] group border border-red-500/50' 
                : 'bg-zinc-900/50 text-zinc-600 border border-zinc-800 cursor-not-allowed backdrop-blur-sm'
              }`}
            >
              {isReady && (
                <div className="absolute inset-0 w-full h-full bg-gradient-to-r from-transparent via-white/20 to-transparent -translate-x-full group-hover:animate-[shimmer_1.5s_infinite]"></div>
              )}
              <span className="relative z-10 flex items-center gap-3">
                {isReady ? (
                  <>
                    <span className="animate-pulse">🔥</span> 
                    Entrar a la Niebla 
                    <span className="animate-pulse">🔥</span>
                  </>
                ) : (
                  'Selecciona 3 y 3'
                )}
              </span>
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
