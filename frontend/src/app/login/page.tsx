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
    <div className="flex flex-col items-center justify-center min-h-screen bg-[url('/dbdhubn.jpg')] bg-cover bg-center bg-no-repeat bg-fixed relative">
      <div className="absolute inset-0 bg-black/40 z-0"></div>
      
      <div className="z-10 p-10 sm:p-14 bg-zinc-900/95 border border-zinc-700 rounded-xl shadow-[0_0_50px_rgba(0,0,0,0.8)] max-w-md w-full mx-4 backdrop-blur-md transform transition-all hover:scale-[1.01] duration-300">
        <div className="flex justify-center mb-8">
          <div className="w-20 h-1 bg-zinc-600 rounded-full animate-pulse"></div>
        </div>
        
        <h1 className="text-4xl md:text-5xl font-black text-zinc-100 mb-8 text-center tracking-[0.2em] uppercase drop-shadow-[0_0_10px_rgba(255,255,255,0.2)]">
          DBD RPG
        </h1>

        {errorMessage && (
          <div className="mb-6 p-4 bg-zinc-950/80 border border-red-500/50 rounded text-red-400 text-sm font-medium text-center animate-pulse">
            {errorMessage}
          </div>
        )}
        
        <div className="space-y-6">
          <div className="group relative">
            <input 
              className="w-full p-4 bg-zinc-950/80 text-zinc-100 border border-zinc-800 rounded-lg focus:outline-none focus:border-zinc-500 focus:ring-1 focus:ring-zinc-500 transition-all duration-300 placeholder-zinc-600 text-lg"
              type="text" placeholder="Sobreviviente o Asesino" value={username} onChange={e => setUsername(e.target.value)}
            />
          </div>
          
          <div className="group relative mb-10">
            <input 
              className="w-full p-4 bg-zinc-950/80 text-zinc-100 border border-zinc-800 rounded-lg focus:outline-none focus:border-zinc-500 focus:ring-1 focus:ring-zinc-500 transition-all duration-300 placeholder-zinc-600 text-lg"
              type="password" placeholder="Contraseña Secreta" value={password} onChange={e => setPassword(e.target.value)}
            />
          </div>
        </div>
        
        <div className="flex flex-col gap-4 mt-10">
          <button 
            onClick={() => handleAuth(true)} 
            className="w-full bg-zinc-800 hover:bg-zinc-700 border border-zinc-600 py-4 rounded-lg font-black text-zinc-100 uppercase tracking-widest transition-all duration-300 hover:shadow-[0_0_20px_rgba(0,0,0,0.5)] active:scale-95 text-lg"
          >
            Entrar a la Niebla
          </button>
          
          <div className="relative flex items-center py-4">
            <div className="flex-grow border-t border-zinc-800"></div>
            <span className="flex-shrink-0 mx-6 text-zinc-500 text-xs uppercase tracking-[0.3em]">O Nuevo Pacto</span>
            <div className="flex-grow border-t border-zinc-800"></div>
          </div>
          
          <button 
            onClick={() => handleAuth(false)} 
            className="w-full border border-zinc-700 bg-zinc-950/50 hover:bg-zinc-800 py-4 rounded-lg font-bold text-zinc-400 hover:text-zinc-100 uppercase tracking-widest transition-all duration-300 active:scale-95 text-lg"
          >
            Firmar Registro
          </button>
        </div>
      </div>
    </div>
  );
}
