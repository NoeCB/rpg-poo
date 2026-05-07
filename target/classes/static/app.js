const API_BASE = 'http://localhost:8080/api/game';

let estadoGlobal = null;
let currentAtacanteIdx = 0;
let isSurviTurn = true; // Empiezan los supervivientes
let modoSeleccion = null; // 'ATACAR' o 'PERK'

document.getElementById('btn-start').addEventListener('click', () => {
    document.getElementById('overlay').classList.add('hidden');
    document.getElementById('game-container').classList.remove('hidden');
    iniciarPartida();
});

async function iniciarPartida() {
    añadirLog("Conectando con la Entidad...", "system");
    try {
        const response = await fetch(`${API_BASE}/start`, { method: 'POST' });
        const data = await response.json();
        estadoGlobal = data;
        renderizarEstado(data);
        añadirLog("¡La simulación ha comenzado!", "system");
        evaluarTurno();
    } catch (error) {
        añadirLog("Error al conectar con el servidor: " + error.message, "system");
    }
}

function renderizarEstado(estado) {
    // Render Survis
    const surviList = document.getElementById('survi-list');
    surviList.innerHTML = '';
    estado.supervivientes.forEach((p, idx) => {
        surviList.appendChild(crearTarjeta(p, idx, 'survi'));
    });

    // Render Killers
    const killerList = document.getElementById('killer-list');
    killerList.innerHTML = '';
    estado.killers.forEach((p, idx) => {
        killerList.appendChild(crearTarjeta(p, idx, 'killer'));
    });

    // Render Logs
    if (estado.logs && estado.logs.length > 0) {
        estado.logs.forEach(l => añadirLog(l, "action"));
        // Limpiamos los logs del estado local para no repetirlos
        estado.logs = []; 
    }

    if (estado.partidaTerminada) {
        document.getElementById('controls').classList.add('hidden');
        añadirLog(`¡LA PARTIDA HA TERMINADO! Ganador: ${estado.ganador}`, "system");
        setTimeout(() => alert(`¡Partida finalizada! Ganador: ${estado.ganador}`), 500);
    }
}

function crearTarjeta(personaje, index, bando) {
    const div = document.createElement('div');
    div.className = `character-card ${bando}-card`;
    div.id = `card-${bando}-${index}`;
    
    const isMuerto = personaje.vidaActual <= 0;
    const pctVida = isMuerto ? 0 : (personaje.vidaActual / personaje.vidaMax) * 100;
    
    let barraClass = bando;
    if (pctVida < 30) barraClass = 'critical';
    else if (pctVida < 60) barraClass = 'low';

    let armaHtml = personaje.arma ? `🗡️ ${personaje.arma.nombreArma}` : '✊ Desarmado';
    if(personaje.defendiendo) armaHtml += " 🛡️ [Defendiendo]";

    div.innerHTML = `
        <div class="char-header">
            <span class="char-name">${personaje.nombrePersonaje}</span>
            <span class="char-hp-text">${isMuerto ? 'MUERTO' : personaje.vidaActual + '/' + personaje.vidaMax}</span>
        </div>
        <div class="hp-bar-container">
            <div class="hp-bar ${barraClass}" style="width: ${pctVida}%"></div>
        </div>
        <div class="char-status">
            <span>${armaHtml}</span>
        </div>
    `;
    return div;
}

function añadirLog(mensaje, tipo) {
    const logContainer = document.getElementById('combat-log');
    const p = document.createElement('p');
    p.className = `log-entry ${tipo === 'system' ? '' : 'survi-action'}`;
    p.innerText = mensaje;
    logContainer.appendChild(p);
    logContainer.scrollTop = logContainer.scrollHeight;
}

function evaluarTurno() {
    if (!estadoGlobal || estadoGlobal.partidaTerminada) return;

    // Lógica simple de turnos: buscamos el siguiente vivo
    let equipoActual = isSurviTurn ? estadoGlobal.supervivientes : estadoGlobal.killers;
    let bandoActual = isSurviTurn ? 'survi' : 'killer';

    // Asegurarnos de que el actual esté vivo
    while (currentAtacanteIdx < equipoActual.length && equipoActual[currentAtacanteIdx].vidaActual <= 0) {
        currentAtacanteIdx++;
    }

    // Si ya pasamos todos los de este equipo, cambiamos de bando
    if (currentAtacanteIdx >= equipoActual.length) {
        isSurviTurn = !isSurviTurn;
        currentAtacanteIdx = 0;
        evaluarTurno(); // Llamada recursiva para el otro equipo
        return;
    }

    // Iluminar la tarjeta del que le toca
    document.querySelectorAll('.character-card').forEach(c => c.classList.remove('active-turn'));
    const activeCard = document.getElementById(`card-${bandoActual}-${currentAtacanteIdx}`);
    if(activeCard) activeCard.classList.add('active-turn');

    const actor = equipoActual[currentAtacanteIdx];
    añadirLog(`Es el turno de ${actor.nombrePersonaje}.`, "system");
}

function setMode(mode) {
    if (!estadoGlobal) return;
    modoSeleccion = mode;
    const targetDiv = document.getElementById('target-selection');
    const perkDiv = document.getElementById('perk-selection');
    
    targetDiv.classList.add('hidden');
    perkDiv.classList.add('hidden');

    if (mode === 'ATACAR') {
        mostrarObjetivos();
    } else if (mode === 'PERK') {
        mostrarPerks();
    }
}

function cancelMode() {
    modoSeleccion = null;
    document.getElementById('target-selection').classList.add('hidden');
    document.getElementById('perk-selection').classList.add('hidden');
}

function mostrarObjetivos() {
    const targetList = document.getElementById('target-list');
    targetList.innerHTML = '';
    const rivales = isSurviTurn ? estadoGlobal.killers : estadoGlobal.supervivientes;
    
    rivales.forEach((r, idx) => {
        if (r.vidaActual > 0) {
            const btn = document.createElement('button');
            btn.className = 'btn-target';
            btn.innerText = r.nombrePersonaje;
            btn.onclick = () => enviarAccion('ATACAR', idx, -1);
            targetList.appendChild(btn);
        }
    });
    
    document.getElementById('target-selection').classList.remove('hidden');
}

function mostrarPerks() {
    const equipo = isSurviTurn ? estadoGlobal.supervivientes : estadoGlobal.killers;
    const actor = equipo[currentAtacanteIdx];
    const perkList = document.getElementById('perk-list');
    perkList.innerHTML = '';

    if (!actor.perks || actor.perks.length === 0) {
        alert("Este personaje no tiene Perks.");
        return;
    }

    actor.perks.forEach((p, idx) => {
        if (p.usos > 0) {
            const btn = document.createElement('button');
            btn.className = 'btn-perk-select';
            btn.innerText = `${p.nombre} (${p.usos} usos)`;
            btn.onclick = () => {
                // Al elegir perk, ahora hay que elegir objetivo
                mostrarObjetivosParaPerk(idx);
            };
            perkList.appendChild(btn);
        }
    });

    document.getElementById('perk-selection').classList.remove('hidden');
}

function mostrarObjetivosParaPerk(perkIdx) {
    document.getElementById('perk-selection').classList.add('hidden');
    const targetList = document.getElementById('target-list');
    targetList.innerHTML = '';
    const rivales = isSurviTurn ? estadoGlobal.killers : estadoGlobal.supervivientes;
    
    rivales.forEach((r, idx) => {
        if (r.vidaActual > 0) {
            const btn = document.createElement('button');
            btn.className = 'btn-target';
            btn.innerText = r.nombrePersonaje;
            btn.onclick = () => enviarAccion('PERK', idx, perkIdx);
            targetList.appendChild(btn);
        }
    });
    
    document.getElementById('target-selection').classList.remove('hidden');
}

function ejecutarDefensa() {
    enviarAccion('DEFENDER', -1, -1);
}

async function enviarAccion(tipoAccion, objetivoIndex, perkIndex) {
    cancelMode();
    const globalAtacanteIdx = isSurviTurn ? currentAtacanteIdx : (estadoGlobal.supervivientes.length + currentAtacanteIdx);
    
    const request = {
        tipoAccion: tipoAccion,
        atacanteIndex: globalAtacanteIdx,
        objetivoIndex: objetivoIndex,
        perkIndex: perkIndex
    };

    try {
        const response = await fetch(`${API_BASE}/action`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(request)
        });
        
        const data = await response.json();
        estadoGlobal = data;
        renderizarEstado(data);
        
        // Avanzar turno
        currentAtacanteIdx++;
        evaluarTurno();

    } catch (error) {
        añadirLog("Error al ejecutar acción.", "system");
    }
}
