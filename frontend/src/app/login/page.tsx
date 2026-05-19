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
        localStorage.setItem('jwt_token', data.token);
        router.push('/dashboard');
      } else if (data && data.error) {
        setErrorMessage(data.error);
      } else if (!res.ok && !data) {
        setErrorMessage('Error de conexión: El servidor backend parece estar apagado o no responde.');
      } else {
        setErrorMessage(isLogin ? 'Credenciales inválidas.' : 'El usuario ya existe o hubo un problema.');
      }
    } catch (error) {
      console.error(error);
      setErrorMessage('Fallo crítico: El servidor del juego está apagado. Verifica tu base de datos y backend.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[url('/maxresdefault.jpg')] bg-cover bg-center bg-no-repeat bg-fixed relative">
      <div className="absolute inset-0 bg-black/55 z-0"></div>
      
      <div className="z-10 p-10 sm:p-14 bg-black/30 border border-zinc-800/80 rounded-2xl shadow-[0_0_50px_rgba(0,0,0,0.95)] max-w-xl w-full mx-4 backdrop-blur-md">
        
        <h1 className="text-4xl md:text-5xl font-normal text-zinc-100 mb-2 text-center tracking-[0.05em] drop-shadow-[0_0_20px_rgba(255,255,255,0.3)] font-[family-name:var(--font-another-danger)]">
          DEAD BY DAYLIGHT
        </h1>

        <p className="text-xs font-normal text-zinc-400 mb-8 text-center tracking-wide font-[family-name:var(--font-special-elite)]">
          RPG basado en el universo de Dead by Daylight
        </p>

        {errorMessage && (
          <div className="mb-6 p-4 bg-zinc-950/80 border border-red-500/50 rounded text-red-400 text-sm font-normal text-center animate-pulse font-[family-name:var(--font-special-elite)]">
            {errorMessage}
          </div>
        )}
        
        <div className="space-y-6">
          <div className="group relative">
            <input 
              className="w-full p-4 bg-black/60 text-zinc-100 border border-zinc-850 rounded-lg focus:outline-none focus:border-zinc-700 focus:ring-1 focus:ring-zinc-700 transition-all duration-300 placeholder-zinc-700 text-lg font-[family-name:var(--font-special-elite)]"
              type="text" placeholder="Sobreviviente o Asesino" value={username} onChange={e => setUsername(e.target.value)}
            />
          </div>
          
          <div className="group relative mb-10">
            <input 
              className="w-full p-4 bg-black/60 text-zinc-100 border border-zinc-850 rounded-lg focus:outline-none focus:border-zinc-700 focus:ring-1 focus:ring-zinc-700 transition-all duration-300 placeholder-zinc-700 text-lg font-[family-name:var(--font-special-elite)]"
              type="password" placeholder="Contraseña Secreta" value={password} onChange={e => setPassword(e.target.value)}
            />
          </div>
        </div>
        
        <div className="flex flex-col gap-4 mt-10">
          <button 
            onClick={() => handleAuth(true)} 
            className="w-full bg-red-950/80 hover:bg-red-900 border border-red-900/60 py-4 rounded-lg font-normal text-red-200 hover:text-white uppercase tracking-widest transition-all duration-300 hover:shadow-[0_0_20px_rgba(239,68,68,0.2)] active:scale-95 text-lg font-[family-name:var(--font-special-elite)]"
          >
            Entrar a la Niebla
          </button>
          
          <div className="relative flex items-center py-4">
            <div className="flex-grow border-t border-zinc-900"></div>
            <span className="flex-shrink-0 mx-6 text-zinc-600 text-xs uppercase tracking-[0.3em] font-[family-name:var(--font-special-elite)]">O Nuevo Pacto</span>
            <div className="flex-grow border-t border-zinc-900"></div>
          </div>
          
          <button 
            onClick={() => handleAuth(false)} 
            className="w-full border border-zinc-800 bg-black/40 hover:bg-zinc-900 py-4 rounded-lg font-normal text-zinc-400 hover:text-zinc-200 uppercase tracking-widest transition-all duration-300 active:scale-95 text-lg font-[family-name:var(--font-special-elite)]"
          >
            Firmar Registro
          </button>
        </div>
      </div>
    </div>
  );
}
