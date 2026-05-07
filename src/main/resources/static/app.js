const API_BASE = 'http://localhost:8080/api/game';

// --- IMAGENES REALES DE DBD ---
const portraitMap = {
    // --- SUPERVIVIENTES ---
    "LeonKennedy": "/leon.jpg",
    "SteveHarrington": "/steve.jpg",
    "FengMin": "/fenming.jpg",
    "SableWard": "/sableward.jpg",
    "Mikaela": "/mikaelareid.jpg",
    "AdaWong": "/adawong.jpg",
    "LaraCroft": "/laracroft.jpg",
    "Nancy": "/nancy.jpg",

    // --- KILLERS ---
    "GhostFace": "/ghostface.jpg",
    "Legion": "/legion.jpg",
    "Onryo": "/onryo.jpg",
    "Animatronico": "/animatronico.jpg",
    "Ghoul": "/ghoul.jpg",
    "Chucky": "/chuckyf.jpg",
    "Wesker": "/wesker.jpg",
    "LaCerda": "/cerda.jpg",

    // --- DEFAULT ---
    "DEFAULT": "/dbd_logo.png"
};
// --- MOCK AWS COGNITO ---
function awsAuthenticateUser() {
    navTo('screen-lobby');
}

// --- NAVEGACIÓN ---
function navTo(screenId) {
    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.getElementById(screenId).classList.add('active');
}

function navToLobby() { navTo('screen-lobby'); }

// --- SELECCIÓN DE PERSONAJES (THE CAMPFIRE) ---
let selectedSurvis = [];
let selectedKillers = [];
let availableSurvis = [];
let availableKillers = [];

async function navToCampfire() {
    navTo('screen-campfire');
    selectedSurvis = [];
    selectedKillers = [];
    updateCounter();
    document.getElementById('btn-start-trial').disabled = true;

    try {
        const res = await fetch(`${API_BASE}/characters`);
        const data = await res.json();
        availableSurvis = data.supervivientes || [];
        availableKillers = data.killers || [];
        renderCharacterList(availableSurvis, 'survi-campfire-list', 'survi');
        renderCharacterList(availableKillers, 'killer-campfire-list', 'killer');
    } catch (e) {
        console.error("Error cargando personajes", e);
        document.getElementById('survi-campfire-list').innerHTML = '<p>Error conectando al backend.</p>';
    }
}

function renderCharacterList(list, containerId, team) {
    const container = document.getElementById(containerId);
    container.innerHTML = '';

    list.forEach(charName => {
        const div = document.createElement('div');
        div.className = 'char-card';
        const imgUrl = portraitMap[charName] || portraitMap["DEFAULT"];

        div.innerHTML = `
            <img src="${imgUrl}" class="char-portrait" id="img-${charName}" alt="${charName}" onerror="this.src='${portraitMap['DEFAULT']}'">
            <p>${charName.replace(/([A-Z])/g, ' $1').trim()}</p>
        `;

        div.querySelector('img').onclick = () => toggleSelection(charName, team);
        container.appendChild(div);
    });
}

function toggleSelection(charName, team) {
    const arr = team === 'survi' ? selectedSurvis : selectedKillers;
    const img = document.getElementById(`img-${charName}`);

    const index = arr.indexOf(charName);
    if (index > -1) {
        arr.splice(index, 1);
        img.classList.remove('selected');
    } else {
        if (arr.length < 3) {
            arr.push(charName);
            img.classList.add('selected');
        }
    }

    updateCounter();
    document.getElementById('btn-start-trial').disabled = (selectedSurvis.length !== 3 || selectedKillers.length !== 3);
}

function updateCounter() {
    document.getElementById('selection-counter').innerText =
        `Seleccionados: S(${selectedSurvis.length}/3) - K(${selectedKillers.length}/3)`;
}

// --- COMBATE (TRIAL) ---
let estadoGlobal = null;
let currentAtacanteIdx = 0;
let isSurviTurn = true;
let isAnimating = false; // Bloquea hovers durante la animacion

async function startManualTrial() {
    navTo('screen-trial');
    document.getElementById('combat-log').innerHTML = '<p class="system-msg">Configurando la Prueba en la Entidad...</p>';

    const request = {
        supervivientes: selectedSurvis,
        killers: selectedKillers
    };

    try {
        const res = await fetch(`${API_BASE}/start-manual`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(request)
        });
        const data = await res.json();
        estadoGlobal = data;
        renderizarEstado(data);
        evaluarTurno();
    } catch (error) {
        añadirLog("Error al iniciar partida en backend: " + error);
    }
}

function renderizarEstado(estado) {
    const sList = document.getElementById('live-survivors');
    const kList = document.getElementById('live-killers');

    sList.innerHTML = '<h4 class="survi-color">SUPERVIVIENTES</h4>';
    estado.supervivientes.forEach((p, idx) => sList.appendChild(createMiniStat(p, 'survi', selectedSurvis[idx])));

    kList.innerHTML = '<h4 class="killer-color">ASESINOS</h4>';
    estado.killers.forEach((p, idx) => kList.appendChild(createMiniStat(p, 'killer', selectedKillers[idx])));

    if (estado.logs && estado.logs.length > 0) {
        estado.logs.forEach(l => añadirLog(l));
        estado.logs = [];
    }

    if (estado.partidaTerminada) {
        document.getElementById('controls').classList.add('hidden');
        añadirLog(`¡PARTIDA FINALIZADA! GANADOR: ${estado.ganador.toUpperCase()}`);
    }
}

function createMiniStat(p, bando, charKey) {
    const div = document.createElement('div');
    const isMuerto = p.vidaActual <= 0;
    div.className = 'mini-stat' + (isMuerto ? ' dead' : '');
    const color = isMuerto ? '#555' : (bando === 'survi' ? 'var(--survi-green)' : 'var(--blood-red)');
    div.innerHTML = `
        <span style="color: ${color}">${p.nombrePersonaje}</span>
        <span style="color: ${color}">${isMuerto ? 'MUERTO' : p.vidaActual + ' HP'}</span>
    `;

    div.onmouseenter = () => {
        if (document.getElementById('selection-panel').classList.contains('hidden') && !isMuerto) {
            previewTarget(p, charKey);
        }
    };
    div.onmouseleave = () => {
        if (document.getElementById('selection-panel').classList.contains('hidden') && !isMuerto) {
            clearPreview();
        }
    };

    return div;
}

function previewTarget(character, portraitKey) {
    if (isAnimating) return;
    const targetBox = document.getElementById('stage-target');
    const stage = document.querySelector('.rpg-stage');
    
    targetBox.innerHTML = `<img src="${portraitMap[portraitKey] || portraitMap['DEFAULT']}" onerror="this.src='${portraitMap['DEFAULT']}'"><div class="char-name-label">${character.nombrePersonaje}</div>`;
    
    stage.classList.add('dual-mode', 'preview-mode');
}

function clearPreview(force = false) {
    if (isAnimating && !force) return;
    const stage = document.querySelector('.rpg-stage');
    stage.classList.remove('dual-mode', 'preview-mode');
}

function añadirLog(mensaje) {
    const logContainer = document.getElementById('combat-log');
    const p = document.createElement('p');
    
    if (mensaje.includes('atacó') || mensaje.includes('daño') || mensaje.includes('CRÍTICO')) {
        p.style.color = 'var(--blood-red)';
    } else if (mensaje.includes('Perk') || mensaje.includes('usó')) {
        p.style.color = '#c942f5'; 
    } else if (mensaje.includes('defensiva') || mensaje.includes('bloqueo')) {
        p.style.color = '#42cbf5'; 
    } else if (mensaje.includes('turno')) {
        p.style.color = '#fff';
        p.style.fontWeight = 'bold';
    } else {
        p.style.color = '#ccc';
    }

    p.innerText = `> ${mensaje}`;
    logContainer.appendChild(p);
    logContainer.scrollTop = logContainer.scrollHeight;
}

function evaluarTurno() {
    if (!estadoGlobal || estadoGlobal.partidaTerminada) return;

    let equipoActual = isSurviTurn ? estadoGlobal.supervivientes : estadoGlobal.killers;
    while (currentAtacanteIdx < equipoActual.length && equipoActual[currentAtacanteIdx].vidaActual <= 0) {
        currentAtacanteIdx++;
    }

    if (currentAtacanteIdx >= equipoActual.length) {
        isSurviTurn = !isSurviTurn;
        currentAtacanteIdx = 0;
        evaluarTurno();
        return;
    }

    const actor = equipoActual[currentAtacanteIdx];

    // UI del Actor
    document.getElementById('current-turn-name').innerText = actor.nombrePersonaje.toUpperCase();
    const pctVida = (actor.vidaActual / actor.vidaMax) * 100;
    const hpBar = document.getElementById('hp-bar');
    hpBar.style.width = `${pctVida}%`;
    if (pctVida > 50) hpBar.style.background = 'var(--survi-green)';
    else if (pctVida > 20) hpBar.style.background = 'orange';
    else hpBar.style.background = 'var(--blood-red)';
    
    document.getElementById('hp-text').innerText = `${actor.vidaActual}/${actor.vidaMax} HP`;

    // Identificar clase real del Java (simplificado extrayendo del array original)
    // Como MotorTrial los mete en el mismo orden:
    let charKey = "DEFAULT";
    if (isSurviTurn) {
        charKey = selectedSurvis[currentAtacanteIdx];
    } else {
        charKey = selectedKillers[currentAtacanteIdx];
    }

    const stageActor = document.getElementById('stage-actor');
    const imgUrl = portraitMap[charKey] || portraitMap["DEFAULT"];
    stageActor.innerHTML = `<img src="${imgUrl}" onerror="this.src='${portraitMap['DEFAULT']}'"><div class="char-name-label">${actor.nombrePersonaje}</div>`;

    añadirLog(`Es el turno de ${actor.nombrePersonaje}`);
}

async function sendAction(tipoAccion, objetivoIndex = -1, perkIndex = -1) {
    isAnimating = true;
    cancelMode();
    const globalAtacanteIdx = isSurviTurn ? currentAtacanteIdx : (estadoGlobal.supervivientes.length + currentAtacanteIdx);
    
    // EVITAR OUT OF BOUNDS EN BACKEND PARA DEFENDER
    const safeObjetivoIndex = objetivoIndex === -1 ? 0 : objetivoIndex;

    // Limpiar estilos de preview por si acaso
    clearPreview(true);

    const actorBox = document.getElementById('stage-actor');
    const targetBox = document.getElementById('stage-target');
    const stage = document.querySelector('.rpg-stage');

    if (objetivoIndex !== -1 && (tipoAccion === 'ATACAR' || tipoAccion === 'PERK')) {
        let targetKey = isSurviTurn ? selectedKillers[objetivoIndex] : selectedSurvis[objetivoIndex];
        let targetName = isSurviTurn ? estadoGlobal.killers[objetivoIndex].nombrePersonaje : estadoGlobal.supervivientes[objetivoIndex].nombrePersonaje;

        targetBox.innerHTML = `<img src="${portraitMap[targetKey] || portraitMap['DEFAULT']}" onerror="this.src='${portraitMap['DEFAULT']}'"><div class="char-name-label">${targetName}</div>`;

        // Limpiar animaciones residuales
        targetBox.classList.remove('anim-damage', 'anim-perk-damage');
        actorBox.classList.remove('anim-attacking-left', 'anim-perk', 'anim-defend');

        stage.classList.add('dual-mode');
        // El CSS se encarga del slide-in suave
        await new Promise(r => setTimeout(r, 800));

        if (tipoAccion === 'ATACAR') {
            actorBox.classList.add('anim-attacking-left');
            await new Promise(r => setTimeout(r, 600)); 
            
            const scratch = document.createElement('div');
            scratch.className = 'scratch-mark';
            scratch.innerText = '///';
            targetBox.appendChild(scratch);
            scratch.classList.add('anim-scratch');

            targetBox.classList.add('anim-damage');
            await new Promise(r => setTimeout(r, 800));
            
            actorBox.classList.remove('anim-attacking-left');
            targetBox.classList.remove('anim-damage');
            if(scratch.parentNode) scratch.parentNode.removeChild(scratch);
        } else if (tipoAccion === 'PERK') {
            actorBox.classList.add('anim-perk');
            await new Promise(r => setTimeout(r, 800)); 
            targetBox.classList.add('anim-perk-damage');
            await new Promise(r => setTimeout(r, 1000));
            actorBox.classList.remove('anim-perk');
            targetBox.classList.remove('anim-perk-damage');
        }

        stage.classList.remove('dual-mode');
        // El CSS se encarga del slide-out suave
        await new Promise(r => setTimeout(r, 800));
    } else if (tipoAccion === 'DEFENDER') {
        actorBox.classList.add('anim-defend');
        await new Promise(r => setTimeout(r, 800));
        actorBox.classList.remove('anim-defend');
    }

    const request = {
        tipoAccion: tipoAccion,
        atacanteIndex: globalAtacanteIdx,
        objetivoIndex: safeObjetivoIndex,
        perkIndex: perkIndex
    };

    try {
        const res = await fetch(`${API_BASE}/action`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(request)
        });
        const data = await res.json();
        estadoGlobal = data;
        renderizarEstado(data);
        currentAtacanteIdx++;
        evaluarTurno();
    } catch (e) {
        añadirLog("Error ejecutando acción.");
    }
    
    isAnimating = false;
}

function setMode(mode) {
    if (!estadoGlobal) return;
    const panel = document.getElementById('selection-panel');
    const list = document.getElementById('selection-list');
    const prompt = document.getElementById('selection-prompt');
    list.innerHTML = '';
    panel.classList.remove('hidden');

    if (mode === 'ATACAR') {
        prompt.innerText = "Selecciona Objetivo:";
        const rivales = isSurviTurn ? estadoGlobal.killers : estadoGlobal.supervivientes;
        const charKeys = isSurviTurn ? selectedKillers : selectedSurvis;
        rivales.forEach((r, idx) => {
            if (r.vidaActual > 0) {
                const btn = document.createElement('button');
                btn.innerText = r.nombrePersonaje;
                btn.onmouseenter = () => previewTarget(r, charKeys[idx]);
                btn.onmouseleave = () => clearPreview();
                btn.onclick = () => { clearPreview(); sendAction('ATACAR', idx); };
                list.appendChild(btn);
            }
        });
    } else if (mode === 'PERK') {
        const equipo = isSurviTurn ? estadoGlobal.supervivientes : estadoGlobal.killers;
        const actor = equipo[currentAtacanteIdx];
        if (!actor.perks || actor.perks.length === 0) {
            prompt.innerText = "No tienes Perks equipadas.";
            return;
        }
        prompt.innerText = "Selecciona Perk:";
        actor.perks.forEach((p, idx) => {
            if (p.usos > 0) {
                const btn = document.createElement('button');
                btn.innerText = `${p.nombre} (${p.usos} usos)`;
                btn.onclick = () => showTargetsForPerk(idx);
                list.appendChild(btn);
            }
        });
    }
}

function showTargetsForPerk(perkIdx) {
    const list = document.getElementById('selection-list');
    const prompt = document.getElementById('selection-prompt');
    list.innerHTML = '';
    prompt.innerText = "Selecciona Objetivo para la Perk:";

    const rivales = isSurviTurn ? estadoGlobal.killers : estadoGlobal.supervivientes;
    const charKeys = isSurviTurn ? selectedKillers : selectedSurvis;
    rivales.forEach((r, idx) => {
        if (r.vidaActual > 0) {
            const btn = document.createElement('button');
            btn.innerText = r.nombrePersonaje;
            btn.onmouseenter = () => previewTarget(r, charKeys[idx]);
            btn.onmouseleave = () => clearPreview();
            btn.onclick = () => { clearPreview(); sendAction('PERK', idx, perkIdx); };
            list.appendChild(btn);
        }
    });
}

function cancelMode() {
    document.getElementById('selection-panel').classList.add('hidden');
}

// --- ARCHIVES ---
async function navToArchives() {
    navTo('screen-archives');
    const grid = document.getElementById('archives-grid');
    grid.innerHTML = '<div class="loading">Cargando logros desde BBDD...</div>';

    try {
        const res = await fetch(`${API_BASE}/achievements`);
        const logros = await res.json();

        grid.innerHTML = '';
        if (logros.length === 0) {
            grid.innerHTML = '<p>No hay logros en la BBDD.</p>';
        }
        logros.forEach(l => {
            const div = document.createElement('div');
            div.className = `achievement-card ${l.conseguido ? 'unlocked' : ''}`;
            div.innerHTML = `
                <h4>${l.nombre}</h4>
                <p>${l.descripcion}</p>
            `;
            grid.appendChild(div);
        });
    } catch (e) {
        grid.innerHTML = '<p>Error conectando a BBDD.</p>';
    }
}
