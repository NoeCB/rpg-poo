'use client';
/* eslint-disable @typescript-eslint/no-explicit-any */

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import toast, { Toaster } from 'react-hot-toast';

const logrosImages = [
  '/logros/1338669.jpeg',
  '/logros/20976.jpg',
  '/logros/5f57916e5c461c931a99da57a7543421.jpeg',
  '/logros/added.jpg',
  '/logros/asco.jpg',
  '/logros/cor.jpg',
  '/logros/gfred.jpg',
  '/logros/hgu.jpeg',
  '/logros/hq720.jpg',
  '/logros/hunts.jpeg',
  '/logros/img.jpg',
  '/logros/jiwoon.jpg',
  '/logros/kazan.jpg',
  '/logros/ni.jpg',
  '/logros/nurse.jpg',
  '/logros/springtrapjpg.jpg'
];

export default function DashboardPage() {
  const router = useRouter();
  const [isLoadModalOpen, setIsLoadModalOpen] = useState(false);
  const [isNewGameModalOpen, setIsNewGameModalOpen] = useState(false);
  const [saves, setSaves] = useState<any[]>([]);
  const [isLoadingSaves, setIsLoadingSaves] = useState(false);

  const [isAchievementsOpen, setIsAchievementsOpen] = useState(false);
  const [achievements, setAchievements] = useState<any[]>([]);
  const [isLoadingAchievements, setIsLoadingAchievements] = useState(false);

  const getAuthToken = () => {
    if (typeof window === 'undefined') return null;
    const cookieToken = document.cookie.split('; ').find(row => row.startsWith('jwt_token='))?.split('=')[1];
    return cookieToken || localStorage.getItem('jwt_token');
  };

  useEffect(() => {
    const token = getAuthToken();
    if (!token) {
      router.push('/login');
    }
  }, []);

  const handleLogout = () => {
    document.cookie = 'jwt_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
    localStorage.removeItem('jwt_token');
    router.push('/login');
  };

  const openNewGameModal = async () => {
    setIsNewGameModalOpen(true);
    setIsLoadingSaves(true);
    try {
      const token = getAuthToken();
      const res = await fetch(`/api/game/saves?t=${Date.now()}`, {
        cache: 'no-store',
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.status === 401 || res.status === 403) {
        handleLogout();
        toast.error("Sesión expirada. Por favor, inicia sesión de nuevo.");
        return;
      }
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

  const startNewGame = (slotId: number, isVacia: boolean) => {
    if (!isVacia) {
      const confirmOverwrite = window.confirm("¡Atención! Esta ranura tiene una partida en curso. Al iniciar una nueva partida se borrará todo el progreso anterior en esta ranura. ¿Quieres continuar?");
      if (!confirmOverwrite) return;
    }
    localStorage.setItem('activeSaveSlot', String(slotId));
    localStorage.removeItem('resumeGame');
    toast.success(`Ranura de Auto-guardado ${slotId} seleccionada.`);
    setTimeout(() => router.push('/play'), 800);
  };

  const openAchievementsModal = async () => {
    setIsAchievementsOpen(true);
    setIsLoadingAchievements(true);
    try {
      const token = getAuthToken();
      const res = await fetch('/api/game/achievements', {
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.status === 401 || res.status === 403) {
        handleLogout();
        toast.error("Sesión expirada. Por favor, inicia sesión de nuevo.");
        return;
      }
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
      const token = getAuthToken();
      const res = await fetch(`/api/game/saves?t=${Date.now()}`, {
        cache: 'no-store',
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.status === 401 || res.status === 403) {
        handleLogout();
        toast.error("Sesión expirada. Por favor, inicia sesión de nuevo.");
        return;
      }
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
      const token = getAuthToken();
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

  const deleteSlot = async (id: number) => {
    if (!confirm(`¿Estás seguro de que deseas eliminar permanentemente los datos de la Ranura ${id}? Esta acción no se puede deshacer.`)) {
      return;
    }

    try {
      const token = getAuthToken();
      const res = await fetch(`/api/game/delete/${id}`, {
        method: 'POST',
        headers: token ? { 'Authorization': `Bearer ${token}` } : {}
      });
      if (res.ok) {
        // Limpiar almacenamiento local si era la partida activa
        localStorage.removeItem('resumeGame');
        localStorage.removeItem('activeSaveSlot');
        
        toast.success(`Ranura ${id} formateada con éxito.`, {
          icon: '🗑️',
          duration: 3000,
          style: {
            background: '#1c1917',
            color: '#f87171',
            border: '1px solid #7f1d1d',
            fontFamily: 'var(--font-special-elite)',
            fontSize: '11px',
            letterSpacing: '0.05em'
          }
        });
        // Refrescar lista de partidas sin caché
        const resSaves = await fetch(`/api/game/saves?t=${Date.now()}`, {
          cache: 'no-store',
          headers: token ? { 'Authorization': `Bearer ${token}` } : {}
        });
        if (resSaves.ok) {
          const data = await resSaves.json();
          setSaves(data);
        }
      } else {
        toast.error("Error al eliminar los datos de la ranura.");
      }
    } catch (error) {
      toast.error("Error de conexión al eliminar.");
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
            onClick={openNewGameModal}
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
              <h2 className="text-3xl font-bold text-red-50 mb-2 group-hover:text-red-400 transition-colors duration-300 tracking-widest uppercase font-[family-name:var(--font-horroroid)]">Nueva Partida</h2>
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
              <h2 className="text-3xl font-bold text-zinc-100 mb-2 group-hover:text-blue-400 transition-colors duration-300 tracking-widest uppercase font-[family-name:var(--font-horroroid)]">Cargar Partida</h2>
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
              <h2 className="text-3xl font-bold text-zinc-100 mb-2 group-hover:text-purple-400 transition-colors duration-300 tracking-widest uppercase font-[family-name:var(--font-horroroid)]">Tus Logros</h2>
              <p className="text-zinc-200 text-xs leading-relaxed font-[family-name:var(--font-special-elite)] tracking-wide">
                Consulta tus hazañas grabadas en la niebla. Comprueba los desafíos que has desbloqueado.
              </p>
            </div>
          </div>

        </main>

        {/* MODAL DE CARGA */}
        {isLoadModalOpen && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
            <div className="bg-stone-950 border-[3px] border-double border-red-950 rounded-none w-full max-w-4xl shadow-[inset_0_0_20px_rgba(0,0,0,0.9),0_0_35px_rgba(100,20,20,0.3)] overflow-hidden animate-in fade-in duration-200">
              <div className="p-6 border-b border-red-950/60 flex justify-between items-center bg-black/50">
                <h3 className="text-2xl md:text-3xl font-normal text-red-600 tracking-[0.08em] uppercase font-[family-name:var(--font-horroroid-bold)] drop-shadow-[0_0_12px_rgba(220,38,38,0.6)]">
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
                        <div key={save.id} className="bg-stone-950/40 border border-stone-900/60 p-6 md:p-8 rounded-none flex items-center justify-between opacity-40">
                          <div>
                            <p className="text-zinc-600 font-normal text-2xl tracking-wider font-[family-name:var(--font-horroroid-bold)]">Ranura {save.id}</p>
                            <p className="text-zinc-700 text-sm font-[family-name:var(--font-special-elite)] tracking-wider">VACÍA</p>
                          </div>
                        </div>
                      ) : (
                        <div key={save.id} className="group bg-stone-950 border border-stone-900/80 hover:border-red-950 p-6 md:p-8 rounded-none flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 transition-all duration-300 hover:shadow-[inset_0_0_20px_rgba(0,0,0,0.85),0_0_20px_rgba(100,20,20,0.2)]">
                          <div>
                            <div className="flex items-center gap-4 mb-2">
                              <p className="text-red-500 font-normal text-2xl md:text-3xl tracking-wider font-[family-name:var(--font-horroroid-bold)]">Ranura {save.id}</p>
                              <span className={`text-xs px-3 py-1 rounded-none font-normal tracking-wider font-[family-name:var(--font-special-elite)] ${save.terminada ? 'bg-red-950 text-red-500 border border-red-900/40' : 'bg-green-950 text-green-500 border border-green-900/40'}`}>
                                {save.terminada ? 'Finalizada' : 'En curso'}
                              </span>
                            </div>
                            <p className="text-zinc-400 text-sm mb-2 font-[family-name:var(--font-special-elite)]">
                              Modo: <span className="text-zinc-200">{save.modoJuego.toUpperCase()}</span> | Ronda: <span className="text-zinc-200">{save.ronda}</span>
                            </p>
                            <p className="text-zinc-500 text-sm flex gap-4 font-[family-name:var(--font-special-elite)]">
                              <span>Survis Vivos: <span className="text-zinc-300">{save.survsVivos}</span></span>
                              <span>Killers Vivos: <span className="text-zinc-300">{save.killersVivos}</span></span>
                            </p>
                          </div>

                          <div className="flex flex-row gap-2">
                            <button
                              onClick={() => loadGame(save.id)}
                              disabled={save.terminada}
                              className={`px-4 py-2 rounded-none font-normal uppercase tracking-widest text-xs transition-all font-[family-name:var(--font-special-elite)] ${save.terminada
                                ? 'bg-zinc-900 text-zinc-600 border border-zinc-800 cursor-not-allowed'
                                : 'bg-[#3a0909] hover:bg-[#520f0f] text-[#fca5a5] border border-[#6b1414] hover:text-white'
                                }`}
                            >
                              {save.terminada ? 'Completada' : 'Cargar'}
                            </button>
                            <button
                              onClick={() => deleteSlot(save.id)}
                              className="px-4 py-2 rounded-none font-normal uppercase tracking-widest text-xs transition-all font-[family-name:var(--font-special-elite)] bg-zinc-950 hover:bg-red-950/80 text-zinc-500 hover:text-red-400 border border-zinc-800 hover:border-red-900 active:scale-95"
                            >
                              Eliminar 🗑️
                            </button>
                          </div>
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
                  <h3 className="text-3xl md:text-4xl font-normal text-white tracking-[0.05em] uppercase font-[family-name:var(--font-another-danger)] drop-shadow-[0_0_15px_rgba(255,255,255,0.3)]">
                    Salon de los Logros
                  </h3>
                  <p className="text-zinc-400 text-xs mt-1 uppercase tracking-widest font-normal font-[family-name:var(--font-special-elite)]">Registro personal de tus hazañas</p>
                </div>
                <button onClick={() => setIsAchievementsOpen(false)} className="text-zinc-500 hover:text-white transition-colors">
                  <span className="text-3xl font-normal font-[family-name:var(--font-special-elite)]">×</span>
                </button>
              </div>

              {/* Barra de progreso de logros */}
              {!isLoadingAchievements && achievements.length > 0 && (
                <div className="px-6 py-4 bg-zinc-900/30 border-b border-zinc-900/80 flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                  <div className="flex-grow w-full">
                    <div className="flex justify-between text-xs font-normal tracking-wider uppercase mb-2">
                      <span className="text-amber-500 text-lg font-[family-name:var(--font-special-elite)]">Progreso del Superviviente</span>
                      <span className="text-zinc-300 font-[family-name:var(--font-special-elite)]">{unlockedCount} / {totalCount} DESBLOQUEADOS</span>
                    </div>
                    <div className="w-full bg-zinc-800 h-2 rounded-full overflow-hidden border border-zinc-700/50 shadow-inner">
                      <div
                        className="bg-gradient-to-r from-amber-700 via-amber-500 to-amber-300 h-full rounded-full transition-all duration-1000 shadow-[0_0_10px_rgba(245,158,11,0.6)]"
                        style={{ width: `${progressPercent}%` }}
                      ></div>
                    </div>
                  </div>
                </div>
              )}

              <div className="p-6 overflow-y-auto flex-1 bg-zinc-950/20">
                {isLoadingAchievements ? (
                  <div className="flex flex-col items-center justify-center py-20">
                    <div className="w-10 h-10 border-2 border-amber-600 border-t-transparent rounded-full animate-spin mb-4"></div>
                    <p className="text-amber-500 font-normal tracking-widest uppercase animate-pulse font-[family-name:var(--font-special-elite)] text-sm">Abriendo el Salón de Logros...</p>
                  </div>
                ) : achievements.length === 0 ? (
                  <div className="text-center py-20">
                    <p className="text-zinc-400 font-normal uppercase tracking-wider font-[family-name:var(--font-special-elite)]">No se encontraron logros en la niebla.</p>
                  </div>
                ) : (
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    {achievements.map((a, index) => (
                      <div
                        key={a.id}
                        className={`group relative h-40 rounded-xl overflow-hidden border transition-all duration-300 flex flex-col justify-end p-5 ${a.conseguido
                          ? 'border-amber-600/60 shadow-[0_0_15px_rgba(245,158,11,0.2)] hover:border-amber-500 hover:-translate-y-1'
                          : 'border-zinc-800/80 opacity-60 grayscale hover:opacity-80 hover:grayscale-[50%]'
                          }`}
                      >
                        {/* Background Image */}
                        <div className="absolute inset-0 z-0">
                          <img
                            src={logrosImages[index % logrosImages.length]}
                            alt={a.nombre}
                            className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
                          />
                          {/* Translucent overlay */}
                          <div className={`absolute inset-0 transition-colors duration-300 ${a.conseguido ? 'bg-black/60 group-hover:bg-black/40' : 'bg-black/85'}`}></div>
                        </div>

                        {/* Content */}
                        <div className="relative z-10 w-full">
                          <div className="flex items-center justify-between gap-2 mb-2">
                            <h4 className={`font-normal tracking-wider truncate font-[family-name:var(--font-special-elite)] ${a.conseguido ? 'text-amber-400 text-lg drop-shadow-[0_0_8px_rgba(245,158,11,0.5)]' : 'text-zinc-400 text-lg'}`}>
                              {a.nombre}
                            </h4>
                          </div>
                          <p className={`text-xs leading-relaxed font-[family-name:var(--font-special-elite)] ${a.conseguido ? 'text-zinc-200' : 'text-zinc-500'}`}>
                            {a.descripcion}
                          </p>
                          {a.conseguido ? (
                            <span className="inline-block mt-3 text-[10px] bg-amber-500/20 border border-amber-500/50 text-amber-300 px-2 py-0.5 rounded font-normal tracking-widest uppercase font-[family-name:var(--font-special-elite)] shadow-[0_0_10px_rgba(245,158,11,0.3)]">
                              DESBLOQUEADO
                            </span>
                          ) : (
                            <span className="inline-block mt-3 text-[10px] bg-zinc-900/80 border border-zinc-700 text-zinc-500 px-2 py-0.5 rounded font-normal tracking-widest uppercase font-[family-name:var(--font-special-elite)]">
                              BLOQUEADO
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
        {/* MODAL DE NUEVA PARTIDA (SELECCIÓN DE RANURA DE AUTOSAVE) */}
        {isNewGameModalOpen && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/85 backdrop-blur-sm animate-in fade-in duration-300">
            <div className="bg-stone-950 border-[3px] border-double border-red-950 rounded-none w-full max-w-2xl shadow-[inset_0_0_25px_rgba(0,0,0,0.95),0_0_40px_rgba(185,28,28,0.25)] overflow-hidden">
              <div className="p-6 border-b border-red-950/60 flex justify-between items-center bg-black/50">
                <div>
                  <h3 className="text-2xl font-normal text-red-500 tracking-widest uppercase font-[family-name:var(--font-horroroid)] drop-shadow-[0_0_10px_rgba(239,68,68,0.4)]">
                    Nueva Partida
                  </h3>
                  <p className="text-zinc-500 text-[10px] uppercase tracking-widest mt-1 font-[family-name:var(--font-special-elite)]">Escoge una ranura para el guardado automático</p>
                </div>
                <button onClick={() => setIsNewGameModalOpen(false)} className="text-zinc-500 hover:text-red-500 transition-colors">
                  <span className="text-2xl font-normal">×</span>
                </button>
              </div>

              <div className="p-6 max-h-[60vh] overflow-y-auto">
                {isLoadingSaves ? (
                  <div className="flex flex-col items-center justify-center py-12">
                    <div className="w-10 h-10 border-2 border-red-900 border-t-transparent rounded-full animate-spin mb-4"></div>
                    <p className="text-red-800 text-xs font-[family-name:var(--font-special-elite)] tracking-widest uppercase animate-pulse">Contactando con la Niebla...</p>
                  </div>
                ) : (
                  <div className="flex flex-col gap-4">
                    {saves.map(save => (
                      <div 
                        key={save.id} 
                        className={`group bg-stone-950/90 border p-5 rounded-none flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 transition-all duration-300 ${
                          save.vacia 
                            ? 'border-zinc-800 hover:border-red-800 hover:shadow-[0_0_15px_rgba(239,68,68,0.15)]' 
                            : 'border-amber-950/60 hover:border-red-700 hover:shadow-[0_0_15px_rgba(239,68,68,0.2)]'
                        }`}
                      >
                        <div>
                          <div className="flex items-center gap-3 mb-1">
                            <p className="text-zinc-100 group-hover:text-red-400 font-normal text-xl tracking-wider font-[family-name:var(--font-horroroid)] transition-colors">Ranura {save.id}</p>
                            {save.vacia ? (
                              <span className="text-[10px] bg-zinc-900 text-zinc-500 border border-zinc-800 px-2 py-0.5 font-[family-name:var(--font-special-elite)]">
                                Vacía - Recomendado
                              </span>
                            ) : (
                              <span className="text-[10px] bg-amber-950/60 text-amber-500 border border-amber-900/30 px-2 py-0.5 font-[family-name:var(--font-special-elite)]">
                                Ocupada - Se sobrescribirá
                              </span>
                            )}
                          </div>
                          {!save.vacia && (
                            <p className="text-zinc-500 text-xs font-[family-name:var(--font-special-elite)]">
                              Modo: <span className="text-zinc-400">{save.modoJuego.toUpperCase()}</span> | Ronda: <span className="text-zinc-400">{save.ronda}</span> | Vivos: <span className="text-zinc-400">S:{save.survsVivos} K:{save.killersVivos}</span>
                            </p>
                          )}
                        </div>

                        <div className="flex flex-row gap-2">
                          <button
                            onClick={() => startNewGame(save.id, save.vacia)}
                            className="px-4 py-2 rounded-none font-normal uppercase tracking-widest text-xs transition-all duration-300 font-[family-name:var(--font-special-elite)] bg-red-950/40 hover:bg-red-900 text-red-400 hover:text-white border border-red-900/60 hover:border-red-600 active:scale-95"
                          >
                            Seleccionar
                          </button>
                          {!save.vacia && (
                            <button
                              onClick={() => deleteSlot(save.id)}
                              className="px-4 py-2 rounded-none font-normal uppercase tracking-widest text-xs transition-all font-[family-name:var(--font-special-elite)] bg-zinc-950 hover:bg-red-950/80 text-zinc-500 hover:text-red-400 border border-zinc-800 hover:border-red-900 active:scale-95"
                            >
                              Eliminar 🗑️
                            </button>
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
