'use client';

import { useRouter } from 'next/navigation';

export default function DashboardPage() {
  const router = useRouter();

  const handleLogout = () => {
    document.cookie = 'jwt_token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
    router.push('/login');
  };

  return (
    <div className="min-h-screen bg-zinc-950 bg-[url('/dbdhubn.jpg')] bg-cover bg-center bg-fixed relative text-white">
      <div className="absolute inset-0 bg-black/85 z-0 backdrop-blur-[2px]"></div>
      
      <div className="relative z-10 p-6 md:p-12 max-w-6xl mx-auto flex flex-col min-h-screen">
        <header className="flex flex-col sm:flex-row justify-between items-center border-b border-red-900/40 pb-6 mb-10 gap-4">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 bg-red-900/30 rounded-full flex items-center justify-center border border-red-600/50 shadow-[0_0_15px_rgba(220,38,38,0.3)]">
              <span className="text-red-500 font-bold text-xl">D</span>
            </div>
            <div>
              <h1 className="text-2xl md:text-3xl font-black text-zinc-100 tracking-wider">PANEL DE CONTROL</h1>
              <p className="text-red-500/80 text-sm font-medium tracking-widest">LA NIEBLA TE ESPERA</p>
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
            className="group bg-gradient-to-br from-zinc-900/90 to-zinc-950/90 border border-zinc-800 hover:border-red-600 p-8 rounded-xl shadow-lg hover:shadow-[0_0_30px_rgba(220,38,38,0.15)] transition-all duration-500 cursor-pointer transform hover:-translate-y-2 flex flex-col items-center text-center relative overflow-hidden" 
            onClick={() => router.push('/play')}
          >
            <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-red-600 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
            <div className="w-20 h-20 rounded-full bg-red-950/30 border border-red-900/50 flex items-center justify-center mb-6 group-hover:scale-110 transition-transform duration-500 shadow-[0_0_20px_rgba(220,38,38,0.2)]">
              <span className="text-red-500 text-3xl font-black drop-shadow-[0_0_8px_rgba(220,38,38,0.8)]">⚔</span>
            </div>
            <h2 className="text-2xl font-bold text-red-50 mb-3 group-hover:text-red-400 transition-colors duration-300 tracking-wide">Nueva Partida</h2>
            <p className="text-zinc-400 text-sm leading-relaxed group-hover:text-zinc-300 transition-colors">
              Adéntrate en la Niebla. Selecciona tu bando, prepara tu equipamiento y enfréntate a la prueba final.
            </p>
          </div>

          {/* Card Cargar Partida */}
          <div className="bg-zinc-900/50 border border-zinc-800/50 p-8 rounded-xl shadow-lg opacity-60 cursor-not-allowed flex flex-col items-center text-center relative overflow-hidden">
            <div className="w-20 h-20 rounded-full bg-zinc-800/50 border border-zinc-700/50 flex items-center justify-center mb-6">
              <span className="text-zinc-500 text-3xl font-black">⏳</span>
            </div>
            <h2 className="text-2xl font-bold text-zinc-300 mb-3 tracking-wide">Cargar Partida</h2>
            <p className="text-zinc-500 text-sm leading-relaxed">
              Tus pactos anteriores están sellados. Próximamente podrás reanudar tus pruebas guardadas.
            </p>
            <div className="absolute -right-8 top-8 bg-zinc-800 text-zinc-400 text-xs font-bold px-10 py-1 rotate-45 border-y border-zinc-700">PRÓXIMAMENTE</div>
          </div>
          
        </main>
      </div>
    </div>
  );
}
