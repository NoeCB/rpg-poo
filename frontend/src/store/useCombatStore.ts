import { create } from 'zustand';

interface CombatState {
  gameState: any; // Mapear a la estructura de GameStateResponse luego
  setGameState: (state: any) => void;
  logs: string[];
  addLog: (log: string) => void;
}

export const useCombatStore = create<CombatState>((set) => ({
  gameState: null,
  setGameState: (state) => set({ gameState: state }),
  logs: [],
  addLog: (log) => set((state) => ({ logs: [...state.logs, log] })),
}));
