// --- DATOS DEL ROSTER ---
const rosterData = {
    killers: [
        { id: 'k1', nombre: 'GHOSTFACE', dif: 'HARD', desc: '¡Si me cuelgas el teléfono, morirás igual que tu madre!', vida: 150, defensa: 15, img: 'ghostface.jpg' },
        { id: 'k2', nombre: 'LEGIÓN', dif: 'MODERATE', desc: '¡Sangra por mí! ¡Sí, así! ¡Oh, me encanta esto! ¡Rebanar y salpicar!', vida: 180, defensa: 25, img: 'legion.jpg' },
        { id: 'k3', nombre: 'ANIMATRÓNICO', dif: 'HARD', desc: 'No es tu carne lo que me sustenta, es tu miedo…', vida: 220, defensa: 20, img: 'animatronico.jpg', bgPos: 'center 60%' },
        { id: 'k4', nombre: 'LA CERDA', dif: 'EASY', desc: 'No me conoces, pero yo sí a ti.', vida: 145, defensa: 18, img: 'cerda.jpg' },
        { id: 'k5', nombre: 'ONRYO', dif: 'EASY', desc: 'No busco perdón... solo quiero que sientas el mismo frío.', vida: 220, defensa: 20, img: 'onryo.jpg', bgPos: 'center 80%' },
        { id: 'k6', nombre: 'WESKER', dif: 'VERY HARD', desc: 'Siete minutos. Es todo el tiempo que puedo dedicarte.', vida: 150, defensa: 20, img: 'wesker.jpg' },
        { id: 'k7', nombre: 'GHOUL', dif: 'HARD', desc: '¿Qué miras, piel suave? ¿Nunca has visto a alguien a quien se le cae la cara a pedazos?', vida: 160, defensa: 15, img: 'ghoul.jpg' },
        { id: 'k8', nombre: 'CHUCKY', dif: 'MODERATE', desc: 'Hola, soy Chucky... ¿quieres jugar?.', vida: 140, defensa: 10, img: 'chuckyf.jpg', bgPos: 'center 40%' }
    ],
    survivors: [
        { id: 's1', nombre: 'ADA WONG', dif: 'EASY', desc: '"¿Crees que me tienes acorralada? Qué tierno. Esto apenas es el calentamiento."', vida: 110, defensa: 15, img: 'adawong.jpg', bgPos: 'center 40%' },
        { id: 's2', nombre: 'FENG MIN', dif: 'EASY', desc: '"Solo eres el jefe de un nivel mediocre. Y yo tengo el récord de no recibir daño."', vida: 100, defensa: 10, img: 'fenming.jpg', bgPos: 'center 20%' },
        { id: 's3', nombre: 'MIKAELA REID', dif: 'EASY', desc: '"He leído los astros y mi destino no es morir hoy. ¿El tuyo? Pinta bastante oscuro."', vida: 100, defensa: 10, img: 'mikaelareid.jpg', bgPos: 'center 20%' },
        { id: 's4', nombre: 'NANCY WHEELER', dif: 'MODERATE', desc: '"He visto monstruos mucho peores que tú arrastrándose en la oscuridad. No voy a huir."', vida: 105, defensa: 12, img: 'nancy.jpg', bgPos: 'center 20%' },
        { id: 's5', nombre: 'LEON S. KENNEDY', dif: 'MODERATE', desc: '"He tenido peores primeros días de trabajo. Si quieres pelea, ven y tómala."', vida: 110, defensa: 15, img: 'leon.jpg', bgPos: 'center 50%' },
        { id: 's6', nombre: 'SABLE WARD', dif: 'HARD', desc: '"¿Intentas asustarme con la muerte y las sombras? Por favor... yo vivo en ellas."', vida: 110, defensa: 30, img: 'sableward.jpg', bgPos: 'center 20%' },
        { id: 's7', nombre: 'LARA CROFT', dif: 'MODERATE', desc: '"He sobrevivido a trampas mortales y a cosas que no creerías. Tú eres solo un obstáculo más."', vida: 120, defensa: 18, img: 'laracroft.jpg', bgPos: 'center 40%' },
        { id: 's8', nombre: 'STEVE HARRINGTON', dif: 'HARD', desc: '"¿Quieres jugar duro? Acércate... mi bate con clavos tiene muchas ganas de saludarte."', vida: 110, defensa: 10, img: 'steve.jpg', bgPos: 'center 20%' }
    ]
};

let currentTab = 'killers';
let personajesSeleccionados = { killers: [], survivors: [] }; // Opcional por si quieres enviar la selección al backend

// --- NAVEGACIÓN ---
document.getElementById('btn-enter').addEventListener('click', () => {
    document.getElementById('overlay').classList.add('hidden');
    document.getElementById('roster-container').classList.remove('hidden');
    renderRoster('killers');
});

// --- SALÓN DE LA FAMA ---
document.getElementById('btn-fame').addEventListener('click', () => {
    document.getElementById('overlay').classList.add('hidden');
    document.getElementById('hall-of-fame-container').classList.remove('hidden');
    cargarLogros();
});

document.getElementById('btn-back-home').addEventListener('click', () => {
    document.getElementById('hall-of-fame-container').classList.add('hidden');
    document.getElementById('overlay').classList.remove('hidden');
});

// Función para conectar el Front-End (JS) con tu Back-End (Java / MySQL)
async function cargarLogros() {
    const grid = document.getElementById('stats-grid');
    grid.innerHTML = '<p style="text-align:center; width:100%; font-size:1.5rem;">Conectando con la base de datos Java...</p>';

    try {
        const response = await fetch('http://localhost:8080/api/game/stats'); 
        if (!response.ok) throw new Error("Error en la respuesta del servidor");
        const stats = await response.json(); 

        grid.innerHTML = '';
        
        const total = stats.partidasTotales > 0 ? stats.partidasTotales : 1;
        const pctKillers = Math.round((stats.victoriasKillers / total) * 100);
        const pctSurvis = Math.round((stats.victoriasSurvis / total) * 100);

        grid.innerHTML = `
            <div class="roster-card" style="grid-column: 1 / -1; cursor: default;">
                <div class="roster-info" style="text-align: center;">
                    <h3 class="roster-name" style="margin-bottom: 15px; font-size: 1.5rem;">DOMINIO DE FACCIÓN</h3>
                    <div style="display: flex; height: 35px; border-radius: 8px; overflow: hidden; font-weight: bold; font-size: 1.1rem; line-height: 35px; box-shadow: 0 0 10px rgba(0,0,0,0.8);">
                        <div style="width: ${pctSurvis}%; background: rgba(0, 210, 255, 0.8); text-align: left; padding-left: 15px; color: white;">Survis: ${pctSurvis}%</div>
                        <div style="width: ${pctKillers}%; background: rgba(255, 51, 51, 0.8); text-align: right; padding-right: 15px; color: white;">Killers: ${pctKillers}%</div>
                    </div>
                </div>
            </div>
            <div class="roster-card" style="cursor: default;">
                <div class="roster-info" style="text-align: center;">
                    <h3 class="roster-name">Partidas Totales</h3>
                    <p class="roster-desc" style="font-size: 2.5rem; font-weight: 900; margin: 10px 0;">${stats.partidasTotales}</p>
                </div>
            </div>
            <div class="roster-card" style="cursor: default;">
                <div class="roster-info" style="text-align: center;">
                    <h3 class="roster-name">Media de Rondas</h3>
                    <p class="roster-desc" style="font-size: 2.5rem; font-weight: 900; margin: 10px 0;">${stats.mediaRondas.toFixed(2)}</p>
                </div>
            </div>
            <div class="roster-card" style="cursor: default; border-bottom: 4px solid var(--killer-color);">
                <div class="roster-info" style="text-align: center;">
                    <h3 class="roster-name">Victorias Killers</h3>
                    <p class="roster-desc" style="font-size: 2.5rem; font-weight: 900; margin: 10px 0; color: var(--killer-color);">${stats.victoriasKillers}</p>
                </div>
            </div>
            <div class="roster-card" style="cursor: default; border-bottom: 4px solid var(--survi-color);">
                <div class="roster-info" style="text-align: center;">
                    <h3 class="roster-name">Victorias Survis</h3>
                    <p class="roster-desc" style="font-size: 2.5rem; font-weight: 900; margin: 10px 0; color: var(--survi-color);">${stats.victoriasSurvis}</p>
                </div>
            </div>
        `;

    } catch (error) {
        grid.innerHTML = '<p style="color:red; text-align:center; width:100%;">Error al conectar con el servidor Java. Verifica que esté en marcha.</p>';
        console.error('Error de conexión:', error);
    }
}

document.getElementById('btn-start-match').addEventListener('click', async () => {
    if (personajesSeleccionados.killers.length !== 3 || personajesSeleccionados.survivors.length !== 3) {
        alert("Debes seleccionar exactamente 3 Killers y 3 Supervivientes para empezar la prueba.");
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/game/start-custom', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                killerIds: personajesSeleccionados.killers,
                survivorIds: personajesSeleccionados.survivors
            })
        });

        if (response.ok) {
            const gameState = await response.json();
            document.getElementById('roster-container').classList.add('hidden');
            document.getElementById('game-container').classList.remove('hidden');
            document.getElementById('combat-log').innerHTML = '';
            renderGameState(gameState);
        } else {
            alert("Error al iniciar la partida en el servidor.");
        }
    } catch (error) {
        console.error(error);
        alert("Error de conexión con el servidor Java.");
    }
});

let estadoGlobal = null;
let turnoActualIndex = 0; // 0-2 Survis, 3-5 Killers
let currentMode = null;
let perkSeleccionadaIndex = -1;

function renderGameState(gameState) {
    estadoGlobal = gameState;
    const surviList = document.getElementById('survi-list');
    const killerList = document.getElementById('killer-list');
    surviList.innerHTML = '';
    killerList.innerHTML = '';

    if (gameState.partidaTerminada) {
        document.getElementById('combat-log').innerHTML += `<p class="system-msg" style="color:#00ff88; font-size:1.2rem;">¡PARTIDA TERMINADA! Ganador: ${gameState.ganador}</p>`;
        document.getElementById('controls').classList.add('hidden');
    }

    if (gameState.supervivientes) {
        gameState.supervivientes.forEach((char, i) => {
            const isTurn = (!gameState.partidaTerminada && turnoActualIndex === i) ? 'border: 2px solid #00d2ff; box-shadow: 0 0 10px #00d2ff;' : '';
            const div = document.createElement('div');
            div.className = 'character-card';
            div.style = isTurn;
            div.innerHTML = `
                <h4>${char.nombrePersonaje}</h4>
                <div class="attr-bar-bg" style="margin-bottom: 5px;"><div class="attr-bar-fill" style="width: ${(char.vidaActual / char.vidaMax) * 100}%; background: var(--survi-color)"></div></div>
                <p>Vida: ${char.vidaActual}/${char.vidaMax}</p>
                <p style="font-size: 0.8rem; margin-top:5px; color:#aaa;">Arma: ${char.arma ? char.arma.nombreArma : 'Ninguna'}</p>
            `;
            surviList.appendChild(div);
        });
    }

    if (gameState.killers) {
        gameState.killers.forEach((char, i) => {
            const isTurn = (!gameState.partidaTerminada && turnoActualIndex === (i + 3)) ? 'border: 2px solid #ff3333; box-shadow: 0 0 10px #ff3333;' : '';
            const div = document.createElement('div');
            div.className = 'character-card';
            div.style = isTurn;
            div.innerHTML = `
                <h4>${char.nombrePersonaje}</h4>
                <div class="attr-bar-bg" style="margin-bottom: 5px;"><div class="attr-bar-fill" style="width: ${(char.vidaActual / char.vidaMax) * 100}%; background: var(--killer-color)"></div></div>
                <p>Vida: ${char.vidaActual}/${char.vidaMax}</p>
                <p style="font-size: 0.8rem; margin-top:5px; color:#aaa;">Arma: ${char.arma ? char.arma.nombreArma : 'Ninguna'}</p>
            `;
            killerList.appendChild(div);
        });
    }
}

function switchTab(tab) {
    currentTab = tab;
    document.getElementById('tab-killers').classList.toggle('active', tab === 'killers');
    document.getElementById('tab-survivors').classList.toggle('active', tab === 'survivors');

    // Cambiar color del botón de la tab activa según bando
    const activeBtn = document.querySelector('.role-tab.active');
    activeBtn.style.borderColor = tab === 'killers' ? 'var(--killer-color)' : 'var(--survi-color)';
    activeBtn.style.background = tab === 'killers' ? 'rgba(255, 51, 51, 0.1)' : 'rgba(0, 210, 255, 0.1)';

    renderRoster(tab);
}

function renderRoster(tab) {
    const grid = document.getElementById('roster-grid');
    grid.innerHTML = '';
    const data = rosterData[tab];
    const colorBarra = tab === 'killers' ? 'var(--killer-color)' : 'var(--survi-color)';
    const icono = tab === 'killers' ? ' ' : ' ';

    data.forEach(char => {
        // En un futuro cambiarás esto para manejar la selección múltiple
        const isSelected = personajesSeleccionados[tab].includes(char.id) ? 'selected' : '';
        const posicionFondo = char.bgPos ? char.bgPos : 'center top';

        const card = document.createElement('div');
        card.className = `roster-card ${isSelected}`;
        card.onclick = () => toggleSelection(card, char.id, tab);

        card.innerHTML = `
            <div class="roster-image-container" style="background-image: url('${char.img}'); background-position: ${posicionFondo};">
                <span class="roster-difficulty">${char.dif}</span>
            </div>
            <div class="roster-info">
                <div class="roster-name-area">
                    <h3 class="roster-name">${char.nombre}</h3>
                    <div style="font-size: 0.8rem; display: inline-block; padding: 2px 5px; border-radius: 4px; background: rgba(0,0,0,0.5);">${icono}</div>
                </div>
                <p class="roster-desc">${char.desc}</p>
                <div class="roster-attributes">
                    <div class="attr-row">
                       
                        <span class="attr-label">VIDA</span>
                        <div class="attr-bar-bg"><div class="attr-bar-fill" style="width: ${(char.vida / 200) * 100}%; background: ${colorBarra}"></div></div>
                        <span class="attr-value">${char.vida}</span>
                    </div>
                    <div class="attr-row">
                        
                        <span class="attr-label">DEFENSA</span>
                        <div class="attr-bar-bg"><div class="attr-bar-fill" style="width: ${(char.defensa / 30) * 100}%; background: ${colorBarra}"></div></div>
                        <span class="attr-value">${char.defensa}</span>
                    </div>
                </div>
            </div>
        `;
        grid.appendChild(card);
    });
}

function toggleSelection(cardElement, id, tab) {
    // Lógica opcional para marcar visualmente personajes seleccionados
    const index = personajesSeleccionados[tab].indexOf(id);
    if (index > -1) {
        personajesSeleccionados[tab].splice(index, 1);
        cardElement.classList.remove('selected');
    } else {
        if (personajesSeleccionados[tab].length >= 3) {
            alert(`Solo puedes seleccionar hasta 3 ${tab === 'killers' ? 'Killers' : 'Supervivientes'}.`);
            return;
        }
        personajesSeleccionados[tab].push(id);
        cardElement.classList.add('selected');
    }
}

function getActorActivo() {
    if (turnoActualIndex < 3) return estadoGlobal.supervivientes[turnoActualIndex];
    return estadoGlobal.killers[turnoActualIndex - 3];
}

function avanzarTurno(nuevoEstado) {
    if (nuevoEstado) estadoGlobal = nuevoEstado;
    if (estadoGlobal.partidaTerminada) {
        renderGameState(estadoGlobal);
        return;
    }
    
    let intentos = 0;
    do {
        turnoActualIndex = (turnoActualIndex + 1) % 6;
        intentos++;
    } while (getActorActivo().vidaActual <= 0 && intentos < 6);
    
    renderGameState(estadoGlobal);
    const actor = getActorActivo();
    const isKiller = turnoActualIndex >= 3;
    const color = isKiller ? 'var(--killer-color)' : 'var(--survi-color)';
    document.getElementById('combat-log').innerHTML += `<p style="color:${color}; margin-top:10px;">> Es el turno de ${actor.nombrePersonaje}</p>`;
}

function setMode(mode) {
    if (!estadoGlobal || estadoGlobal.partidaTerminada) return;
    const actor = getActorActivo();
    if (actor.vidaActual <= 0) return avanzarTurno();
    
    currentMode = mode;
    if (mode === 'ATACAR') mostrarObjetivos();
    else if (mode === 'PERK') mostrarPerks();
}

async function ejecutarDefensa() {
    if (!estadoGlobal || estadoGlobal.partidaTerminada) return;
    await enviarAccion({ tipoAccion: 'DEFENDER', atacanteIndex: turnoActualIndex, objetivoIndex: 0, perkIndex: 0 });
}

function mostrarObjetivos() {
    document.getElementById('target-selection').classList.remove('hidden');
    document.getElementById('perk-selection').classList.add('hidden');
    const targetList = document.getElementById('target-list');
    targetList.innerHTML = '';
    
    const isSurvi = turnoActualIndex < 3;
    const rivales = isSurvi ? estadoGlobal.killers : estadoGlobal.supervivientes;
    
    rivales.forEach((rival, i) => {
        if (rival.vidaActual > 0) {
            const btn = document.createElement('button');
            btn.className = 'btn-action';
            btn.textContent = rival.nombrePersonaje;
            btn.onclick = () => ejecutarAtaque(i);
            targetList.appendChild(btn);
        }
    });
}

async function ejecutarAtaque(objetivoIndex) {
    document.getElementById('target-selection').classList.add('hidden');
    await enviarAccion({ tipoAccion: 'ATACAR', atacanteIndex: turnoActualIndex, objetivoIndex: objetivoIndex, perkIndex: 0 });
}

function mostrarPerks() {
    const actor = getActorActivo();
    if (!actor.perks || actor.perks.length === 0) {
        alert("Este personaje no tiene perks disponibles.");
        return;
    }
    document.getElementById('perk-selection').classList.remove('hidden');
    document.getElementById('target-selection').classList.add('hidden');
    const perkList = document.getElementById('perk-list');
    perkList.innerHTML = '';
    
    actor.perks.forEach((perk, i) => {
        if (perk.usos > 0) {
            const btn = document.createElement('button');
            btn.className = 'btn-action';
            btn.textContent = `${perk.nombre} (${perk.usos})`;
            btn.onclick = () => {
                perkSeleccionadaIndex = i;
                document.getElementById('perk-selection').classList.add('hidden');
                mostrarObjetivos();
            };
            perkList.appendChild(btn);
        }
    });
}

function cancelMode() {
    currentMode = null;
    document.getElementById('target-selection').classList.add('hidden');
    document.getElementById('perk-selection').classList.add('hidden');
}

async function enviarAccion(payload) {
    // Si era modo Perk, ajustamos el payload antes de enviarlo
    if (currentMode === 'PERK') {
        payload.tipoAccion = 'PERK';
        payload.perkIndex = perkSeleccionadaIndex;
    }
    
    try {
        const response = await fetch('http://localhost:8080/api/game/action', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        if (response.ok) {
            const gameState = await response.json();
            const logDiv = document.getElementById('combat-log');
            if (gameState.logs && gameState.logs.length > 0) {
                gameState.logs.forEach(msg => logDiv.innerHTML += `<p>> ${msg}</p>`);
            }
            logDiv.scrollTop = logDiv.scrollHeight;
            cancelMode();
            avanzarTurno(gameState);
        }
    } catch(err) {
        console.error(err);
        alert("Error de conexión al enviar la acción.");
    }
}