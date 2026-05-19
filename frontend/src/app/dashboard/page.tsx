'use client';
/* eslint-disable @typescript-eslint/no-explicit-any */

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import toast, { Toaster } from 'react-hot-toast';

export default function DashboardPage() {
  const router = useRouter();
  const [isLoadModalOpen, setIsLoadModalOpen] = useState(false);
  const [saves, setSaves] = useState<any[]>([]);
  const [isLoadingSaves, setIsLoadingSaves] = useState(false);

  const [isAchievementsOpen, setIsAchievementsOpen] = useState(false);
  const [achievements, setAchievements] = useState<any[]>([]);
  const [isLoadingAchievements, setIsLoadingAchievements] = useState(false);

  const handleLogout = () => {
    document.cookie = 'jwt_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
    router.push('/login');
  };

  const openAchievementsModal = async () => {
    setIsAchievementsOpen(true);
    setIsLoadingAchievements(true);
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch('/api/game/achievements', {
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.ok) {
        const data = await res.json();
        setAchievements(data);
      } else {
        toast.error("Error al obtener logros.");
      }
    } catch (error) {
      toast.error("Error de red al conectar.");
    } finally {
      setIsLoadingAchievements(false);
    }
  };

  const openLoadModal = async () => {
    setIsLoadModalOpen(true);
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
        toast.error("Error al obtener partidas guardadas.");
      }
    } catch (error) {
      toast.error("Error de red al conectar.");
    } finally {
      setIsLoadingSaves(false);
    }
  };

  const loadGame = async (id: number) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
      const res = await fetch(`/api/game/load/${id}`, {
        method: 'POST',
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.ok) {
        toast.success("Partida cargada con éxito.");
        localStorage.setItem('resumeGame', 'true');
        setTimeout(() => router.push('/trial'), 1000);
      } else {
        toast.error("La partida no pudo ser cargada o está corrupta.");
      }
    } catch (error) {
      toast.error("Error de red al cargar.");
    }
  };

  const unlockedCount = achievements.filter(a => a.conseguido).length;
  const totalCount = achievements.length || 20;
  const progressPercent = totalCount > 0 ? (unlockedCount / totalCount) * 100 : 0;

  return (
    <div className="min-h-screen bg-zinc-950 bg-[url('/dbdhubn.jpg')] bg-cover bg-center bg-fixed relative text-white">
      <div className="absolute inset-0 bg-black/70 z-0 backdrop-blur-[1px]"></div>

      <div className="relative z-10 p-6 md:p-12 max-w-6xl mx-auto flex flex-col min-h-screen">
        <header className="flex flex-col sm:flex-row justify-between items-center border-b border-red-900/40 pb-6 mb-10 gap-4">
          <div className="flex items-center gap-4">
            <div>
              <h1 className="text-5xl font-normal text-white tracking-[0.05em] font-[family-name:var(--font-another-danger)] drop-shadow-[0_0_20px_rgba(255,255,255,0.4)]">MENU PRINCIPAL</h1>
              <p className="text-red-500/85 text-xs font-black tracking-[0.2em] mt-2 uppercase font-[family-name:var(--font-special-elite)]">LA NIEBLA TE ESPERA</p>
            </div>
          </div>
          <button
            onClick={handleLogout}
            className="px-6 py-2 border border-zinc-700 text-zinc-300 hover:text-white hover:bg-zinc-800 hover:border-zinc-500 rounded font-medium transition-all duration-300 active:scale-95"
          >
            Escapar
          </button>
        </header>

        <main className="flex-1 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 lg:gap-8">

          {/* Card Nueva Partida */}
          <div
            className="group relative h-[480px] rounded-2xl overflow-hidden border border-zinc-800/85 hover:border-red-600/85 shadow-lg hover:shadow-[0_0_35px_rgba(220,38,38,0.25)] transition-all duration-500 cursor-pointer transform hover:-translate-y-2 flex flex-col justify-end p-6"
            onClick={() => router.push('/play')}
          >
            {/* Card Background Image (occupies the whole card) */}
            <div className="absolute inset-0 z-0">
              <img
                src="/gf.jpg"
                alt="Nueva Partida"
                className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 brightness-[0.7] group-hover:brightness-[0.9]"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black via-black/35 to-transparent opacity-90 group-hover:opacity-85 transition-opacity"></div>
            </div>

            {/* Overlaid themed box with text */}
            <div className="relative z-10 bg-red-950/75 hover:bg-red-900/85 border border-red-900/40 backdrop-blur-[6px] p-5 rounded-xl text-center shadow-[0_4px_20px_rgba(220,38,38,0.2)] transition-all duration-300">
              <h2 className="text-2xl font-black text-red-50 mb-2 group-hover:text-red-400 transition-colors duration-300 tracking-wide">Nueva Partida</h2>
              <p className="text-zinc-200 text-xs leading-relaxed font-[family-name:var(--font-special-elite)] tracking-wide">
                Adéntrate en la Niebla. Selecciona tu bando, prepara tu equipamiento y enfréntate a la prueba final.
              </p>
            </div>
          </div>

          {/* Card Cargar Partida */}
          <div
            className="group relative h-[480px] rounded-2xl overflow-hidden border border-zinc-800/85 hover:border-blue-600/85 shadow-lg hover:shadow-[0_0_35px_rgba(59,130,246,0.25)] transition-all duration-500 cursor-pointer transform hover:-translate-y-2 flex flex-col justify-end p-6"
            onClick={openLoadModal}
          >
            {/* Card Background Image (occupies the whole card) */}
            <div className="absolute inset-0 z-0">
              <img
                src="/head.jpg"
                alt="Cargar Partida"
                className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 brightness-[0.7] group-hover:brightness-[0.9]"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black via-black/35 to-transparent opacity-90 group-hover:opacity-85 transition-opacity"></div>
            </div>

            {/* Overlaid themed box with text */}
            <div className="relative z-10 bg-blue-950/75 hover:bg-blue-900/85 border border-blue-900/40 backdrop-blur-[6px] p-5 rounded-xl text-center shadow-[0_4px_20px_rgba(59,130,246,0.2)] transition-all duration-300">
              <h2 className="text-2xl font-black text-zinc-100 mb-2 group-hover:text-blue-400 transition-colors duration-300 tracking-wide">Cargar Partida</h2>
              <p className="text-zinc-200 text-xs leading-relaxed font-[family-name:var(--font-special-elite)] tracking-wide">
                Reanuda tus pruebas donde las dejaste. Accede a las ranuras de guardado de la Entidad.
              </p>
            </div>
          </div>

          {/* Card Logros */}
          <div
            className="group relative h-[480px] rounded-2xl overflow-hidden border border-zinc-800/85 hover:border-purple-600/85 shadow-lg hover:shadow-[0_0_35px_rgba(168,85,247,0.25)] transition-all duration-500 cursor-pointer transform hover:-translate-y-2 flex flex-col justify-end p-6"
            onClick={openAchievementsModal}
          >
            {/* Card Background Image (occupies the whole card) */}
            <div className="absolute inset-0 z-0">
              <img
                src="/hunter.jpg"
                alt="Logros"
                className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 brightness-[0.7] group-hover:brightness-[0.9]"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black via-black/35 to-transparent opacity-90 group-hover:opacity-85 transition-opacity"></div>
            </div>

            {/* Overlaid themed box with text */}
            <div className="relative z-10 bg-purple-950/75 hover:bg-purple-900/85 border border-purple-900/40 backdrop-blur-[6px] p-5 rounded-xl text-center shadow-[0_4px_20px_rgba(168,85,247,0.2)] transition-all duration-300">
              <h2 className="text-2xl font-black text-zinc-100 mb-2 group-hover:text-purple-400 transition-colors duration-300 tracking-wide">Tus Logros</h2>
              <p className="text-zinc-200 text-xs leading-relaxed font-[family-name:var(--font-special-elite)] tracking-wide">
                Consulta tus hazañas grabadas en la niebla. Comprueba los desafíos que has desbloqueado.
              </p>
            </div>
          </div>
          
        </main>

        {/* MODAL DE CARGA */}
        {isLoadModalOpen && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
            <div className="bg-stone-950 border-[3px] border-double border-red-950 rounded-none w-full max-w-2xl shadow-[inset_0_0_20px_rgba(0,0,0,0.9),0_0_35px_rgba(100,20,20,0.3)] overflow-hidden animate-in fade-in duration-200">
              <div className="p-6 border-b border-red-950/60 flex justify-between items-center bg-black/50">
                <h3 className="text-2xl font-normal text-red-600 tracking-widest uppercase font-[family-name:var(--font-horroroid)] drop-shadow-[0_0_10px_rgba(220,38,38,0.5)]">
                  Ranuras de Guardado
                </h3>
                <button onClick={() => setIsLoadModalOpen(false)} className="text-zinc-500 hover:text-red-500 transition-colors">
                  <span className="text-2xl font-normal">×</span>
                </button>
              </div>

              <div className="p-6 max-h-[60vh] overflow-y-auto">
                {isLoadingSaves ? (
                  <div className="flex flex-col items-center justify-center py-12">
                    <div className="w-10 h-10 border-2 border-red-900 border-t-transparent rounded-full animate-spin mb-4"></div>
                    <p className="text-red-800 text-xs font-[family-name:var(--font-special-elite)] tracking-widest uppercase animate-pulse">Contactando con la Entidad...</p>
                  </div>
                ) : saves.length === 0 ? (
                  <div className="text-center py-12">
                    <p className="text-zinc-600 text-xs font-[family-name:var(--font-special-elite)] uppercase tracking-widest">No hay registros de partidas selladas en la Niebla.</p>
                  </div>
                ) : (
                  <div className="flex flex-col gap-4">
                    {saves.map(save => (
                      save.vacia ? (
                        <div key={save.id} className="bg-stone-950/40 border border-stone-900/60 p-4 rounded-none flex items-center justify-between opacity-40">
                          <div>
                            <p className="text-zinc-600 font-normal text-lg tracking-wider font-[family-name:var(--font-horroroid)]">Ranura {save.id}</p>
                            <p className="text-zinc-700 text-xs font-[family-name:var(--font-special-elite)] tracking-wider">VACÍA</p>
                          </div>
                        </div>
                      ) : (
                        <div key={save.id} className="group bg-stone-950 border border-stone-900/80 hover:border-red-950 p-5 rounded-none flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 transition-all duration-300 hover:shadow-[inset_0_0_15px_rgba(0,0,0,0.8),0_0_15px_rgba(100,20,20,0.15)]">
                          <div>
                            <div className="flex items-center gap-3 mb-1">
                              <p className="text-red-500 font-normal text-xl tracking-wider font-[family-name:var(--font-horroroid)]">Ranura {save.id}</p>
                              <span className={`text-xs px-2 py-0.5 rounded-none font-normal tracking-wider font-[family-name:var(--font-special-elite)] ${save.terminada ? 'bg-red-950 text-red-500 border border-red-900/40' : 'bg-green-950 text-green-500 border border-green-900/40'}`}>
                                {save.terminada ? 'Finalizada' : 'En curso'}
                              </span>
                            </div>
                            <p className="text-zinc-500 text-xs mb-1 font-[family-name:var(--font-special-elite)]">
                              Modo: <span className="text-zinc-400">{save.modoJuego.toUpperCase()}</span> | Ronda: <span className="text-zinc-400">{save.ronda}</span>
                            </p>
                            <p className="text-zinc-600 text-xs flex gap-3 font-[family-name:var(--font-special-elite)]">
                              <span>Survis Vivos: <span className="text-zinc-400">{save.survsVivos}</span></span>
                              <span>Killers Vivos: <span className="text-zinc-400">{save.killersVivos}</span></span>
                            </p>
                          </div>

                          <button
                            onClick={() => loadGame(save.id)}
                            disabled={save.terminada}
                            className={`px-6 py-2.5 rounded-none font-normal uppercase tracking-widest text-xs transition-all font-[family-name:var(--font-special-elite)] ${save.terminada
                              ? 'bg-zinc-900 text-zinc-600 border border-zinc-800 cursor-not-allowed'
                              : 'bg-[#3a0909] hover:bg-[#520f0f] text-[#fca5a5] border border-[#6b1414] hover:text-white'
                              }`}
                          >
                            {save.terminada ? 'Completada' : 'Cargar'}
                          </button>
                        </div>
                      )
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
        )}
        {/* MODAL DE LOGROS */}
        {isAchievementsOpen && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/85 backdrop-blur-sm">
            <div className="bg-zinc-950 border border-zinc-800 rounded-2xl w-full max-w-3xl shadow-2xl overflow-hidden animate-in fade-in zoom-in duration-300 flex flex-col max-h-[85vh]">
              <div className="p-6 border-b border-zinc-800 flex justify-between items-center bg-black/40">
                <div>
                  <h3 className="text-2xl font-black text-transparent bg-clip-text bg-gradient-to-r from-amber-400 to-amber-200 tracking-widest uppercase">
                    Salón de los Logros
                  </h3>
                  <p className="text-zinc-500 text-xs mt-1 uppercase tracking-widest font-bold">Registro personal de tus hazañas</p>
                </div>
                <button onClick={() => setIsAchievementsOpen(false)} className="text-zinc-500 hover:text-white transition-colors">
                  <span className="text-2xl font-bold">×</span>
                </button>
              </div>
              
              {/* Barra de progreso de logros */}
              {!isLoadingAchievements && achievements.length > 0 && (
                <div className="px-6 py-4 bg-zinc-900/30 border-b border-zinc-900/80 flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                  <div className="flex-grow w-full">
                    <div className="flex justify-between text-xs font-black tracking-wider uppercase mb-1">
                      <span className="text-amber-400">Progreso del Superviviente</span>
                      <span className="text-zinc-300">{unlockedCount} / {totalCount} Desbloqueados</span>
                    </div>
                    <div className="w-full bg-zinc-800 h-2.5 rounded-full overflow-hidden border border-zinc-700/55 shadow-inner">
                      <div 
                        className="bg-gradient-to-r from-amber-600 via-amber-400 to-yellow-300 h-full rounded-full transition-all duration-1000 shadow-[0_0_10px_rgba(245,158,11,0.5)]"
                        style={{ width: `${progressPercent}%` }}
                      ></div>
                    </div>
                  </div>
                </div>
              )}

              <div className="p-6 overflow-y-auto flex-1 bg-zinc-950/20">
                {isLoadingAchievements ? (
                  <div className="flex flex-col items-center justify-center py-20">
                    <div className="w-12 h-12 border-4 border-amber-500 border-t-transparent rounded-full animate-spin mb-4"></div>
                    <p className="text-zinc-400 font-bold tracking-widest uppercase animate-pulse">Abriendo el Salón de Logros...</p>
                  </div>
                ) : achievements.length === 0 ? (
                  <div className="text-center py-20">
                    <span className="text-5xl block mb-4">🕸️</span>
                    <p className="text-zinc-400 font-bold uppercase tracking-wider">No se encontraron logros en la niebla.</p>
                  </div>
                ) : (
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    {achievements.map(a => (
                      <div 
                        key={a.id} 
                        className={`p-4 rounded-xl border transition-all duration-300 flex items-start gap-4 ${
                          a.conseguido 
                          ? 'bg-gradient-to-br from-zinc-900 to-amber-950/10 border-amber-600/40 hover:border-amber-500 shadow-[0_0_15px_rgba(245,158,11,0.05)]' 
                          : 'bg-zinc-900/20 border-zinc-800/80 opacity-40 hover:opacity-60 grayscale'
                        }`}
                      >
                        <div className={`w-12 h-12 rounded-lg flex items-center justify-center flex-shrink-0 border font-bold text-xl ${
                          a.conseguido 
                          ? 'bg-amber-950/50 border-amber-600/50 text-amber-400 shadow-[0_0_10px_rgba(245,158,11,0.2)]' 
                          : 'bg-zinc-950 border-zinc-800 text-zinc-600'
                        }`}>
                          {a.conseguido ? '🏆' : '🔒'}
                        </div>
                        <div className="flex-1 min-w-0">
                          <div className="flex items-center justify-between gap-2 mb-1">
                            <h4 className={`font-black tracking-wide truncate ${a.conseguido ? 'text-amber-100 text-sm md:text-base' : 'text-zinc-400 text-sm'}`}>
                              {a.nombre}
                            </h4>
                          </div>
                          <p className={`text-xs leading-relaxed ${a.conseguido ? 'text-zinc-300' : 'text-zinc-500'}`}>
                            {a.descripcion}
                          </p>
                          {a.conseguido && (
                            <span className="inline-block mt-2 text-[10px] bg-amber-500/10 border border-amber-500/30 text-amber-400 px-2 py-0.5 rounded font-black tracking-wider uppercase">
                              DESBLOQUEADO
                            </span>
                          )}
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
