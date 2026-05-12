'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  const handleAuth = async (isLogin: boolean) => {
    setErrorMessage('');
    
    if (!username || !password) {
      setErrorMessage('Debes introducir un nombre y una contraseña.');
      return;
    }

    setIsLoading(true);
    try {
      const endpoint = isLogin ? '/api/auth/login' : '/api/auth/register';
      const res = await fetch(endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      const data = await res.json().catch(() => null);

      if (res.ok && data?.token) {
        document.cookie = `jwt_token=${data.token}; path=/;`;
        router.push('/dashboard');
      } else {
        setErrorMessage(data?.error || (isLogin ? 'Credenciales inválidas.' : 'El usuario ya existe.'));
      }
    } catch (error) {
      console.error(error);
      setErrorMessage('Fallo crítico: El servidor del juego está apagado. Verifica tu base de datos y backend.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[url('https://c4.wallpaperflare.com/wallpaper/528/773/953/dead-by-daylight-logo-4k-wallpaper-preview.jpg')] bg-cover bg-center bg-no-repeat bg-fixed relative">
      <div className="absolute inset-0 bg-black/70 backdrop-blur-sm z-0"></div>
      
      <div className="z-10 p-8 sm:p-10 bg-zinc-950/80 border border-red-900/50 rounded-lg shadow-2xl shadow-red-900/20 max-w-sm w-full mx-4 backdrop-blur-md transform transition-all hover:scale-[1.01] duration-300">
        <div className="flex justify-center mb-6">
          <div className="w-16 h-1 bg-red-600 rounded-full animate-pulse"></div>
        </div>
        
        <h1 className="text-4xl font-black text-red-600 mb-6 text-center tracking-widest uppercase drop-shadow-[0_0_15px_rgba(220,38,38,0.6)]">
          DBD RPG
        </h1>

        {errorMessage && (
          <div className="mb-6 p-3 bg-red-950/80 border border-red-500 rounded text-red-400 text-sm font-medium text-center animate-pulse">
            {errorMessage}
          </div>
        )}
        
        <div className="space-y-4">
          <div className="group relative">
            <input 
              className="w-full p-3 bg-zinc-900/80 text-zinc-100 border border-zinc-800 rounded focus:outline-none focus:border-red-500 focus:ring-1 focus:ring-red-500 transition-all duration-300 placeholder-zinc-600"
              type="text" placeholder="Sobreviviente o Asesino" value={username} onChange={e => setUsername(e.target.value)}
            />
          </div>
          
          <div className="group relative mb-8">
            <input 
              className="w-full p-3 bg-zinc-900/80 text-zinc-100 border border-zinc-800 rounded focus:outline-none focus:border-red-500 focus:ring-1 focus:ring-red-500 transition-all duration-300 placeholder-zinc-600"
              type="password" placeholder="Contraseña Secreta" value={password} onChange={e => setPassword(e.target.value)}
            />
          </div>
        </div>
        
        <div className="flex flex-col gap-3 mt-8">
          <button 
            onClick={() => handleAuth(true)} 
            className="w-full bg-red-700 hover:bg-red-600 py-3 rounded font-bold text-white uppercase tracking-wider transition-all duration-300 hover:shadow-[0_0_15px_rgba(220,38,38,0.5)] active:scale-95"
          >
            Entrar a la Niebla
          </button>
          
          <div className="relative flex items-center py-2">
            <div className="flex-grow border-t border-zinc-800"></div>
            <span className="flex-shrink-0 mx-4 text-zinc-600 text-xs uppercase tracking-widest">O Nuevo Pacto</span>
            <div className="flex-grow border-t border-zinc-800"></div>
          </div>
          
          <button 
            onClick={() => handleAuth(false)} 
            className="w-full border border-red-900/50 hover:border-red-500 bg-zinc-900/50 hover:bg-red-950/30 py-3 rounded font-bold text-red-500 hover:text-red-400 uppercase tracking-wider transition-all duration-300 active:scale-95"
          >
            Firmar Registro
          </button>
        </div>
      </div>
    </div>
  );
}
