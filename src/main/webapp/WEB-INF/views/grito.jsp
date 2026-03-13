<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/grito.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">

    <title>Verificação de Monstro - Monsters University</title>
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
</header>

<div id="fundo">
    <div id="medidor">
        <img src="${pageContext.request.contextPath}/assets/imgs/medidor-grito.png" alt="Medidor" id="medidor-img">
        <div id="barra-vermelha"></div>
    </div>

    <div id="conteudo">
        <h1>Verificação de Monstro</h1>

        <div id="resultado-box">
            <span id="resultado">Para se Cadastrar confirme que é um monstro!</span>

            <h2 id="nivel-titulo">FAÇA O TESTE</h2>

            <p id="mensagem">Carregue seus gritos para confirmarmos que você é um monstro.</p>

            <div id="botoes">
                <button id="btn-gritar">GRITAR</button>
                <span id="contador"></span>
                <button id="btn-sair" onclick="location.href='${pageContext.request.contextPath}/'">SAIR</button>
            </div>
        </div>

        <!-- Imagens dos monstros (usando os mesmos IDs do seu CSS para manter posição) -->
        <img src="${pageContext.request.contextPath}/assets/imgs/mike-grito.png" alt="nivel-1" id="mike-grito" class="imgs-box">
        <img src="${pageContext.request.contextPath}/assets/imgs/bob-grito.png" alt="nivel-1-abaixo" id="bob-grito" class="imgs-box">
        <img src="${pageContext.request.contextPath}/assets/imgs/patrick-grito.png" alt="nivel-2" id="patrick-grito" class="imgs-box">
        <img src="${pageContext.request.contextPath}/assets/imgs/surpresa.png" alt="nivel-3-destaque" id="surpresa" class="imgs-box">
        <img src="${pageContext.request.contextPath}/assets/imgs/sulley-400.png" alt="nivel-3-rei" id="sulley-grito" class="imgs-box">
        <img src="${pageContext.request.contextPath}/assets/imgs/elite-grito.png" alt="nivel-3-elite" id="lisete-grito" class="imgs-box">
    </div>
</div>

<script>
    const RECORD_SECONDS = 5;
    const SCALE = 1000;

    const thresholdsScaled = [200, 400, 600, 800, 1000, 1200];

    const LEVELS = [
        { label: "VÍTIMA DE BULLYING", img: "mike-grito" },
        { label: "ABAIXO DA MÉDIA", img: "bob-grito" },
        { label: "NA MÉDIA", img: "patrick-grito" },
        { label: "DESTAQUE DA TURMA", img: "surpresa" },
        { label: "REI DO CAMPUS", img: "sulley-grito" },
        { label: "ELITE MONSTRUOSA", img: "lisete-grito" }
    ];

    const N_TICKS = thresholdsScaled.length;
    const btnGritar = document.getElementById('btn-gritar');
    const barra = document.getElementById('barra-vermelha');
    const contador = document.getElementById('contador');
    const nivelTitulo = document.getElementById('nivel-titulo');
    const mensagem = document.getElementById('mensagem');
    const medidor = document.getElementById('medidor');
    const resultadoBox = document.getElementById('resultado-box');
    const resultadoT = document.getElementById('resultado')

    let audioContext, analyser, sourceNode, streamRef;
    let peakTick = 0;
    let rafId = null;

    function resetarInterface() {
        peakTick = 0;
        barra.style.height = "0px";

        resultadoT.textContent = "Para se Cadastrar confirme que é um monstro!";
        nivelTitulo.textContent = "FAÇA O TESTE";
        mensagem.textContent = "Carregue seus gritos para confirmarmos que você é um monstro.";

        document.querySelectorAll('.imgs-box').forEach(img => img.style.display = 'none');

        btnGritar.textContent = "GRITAR";
        btnGritar.disabled = false;
    }

    function calcularRMS(buffer) {
        let sum = 0;
        for (let i = 0; i < buffer.length; i++) sum += buffer[i] * buffer[i];
        return Math.sqrt(sum / buffer.length);
    }

    function calcularAlturaMaximaBarra() {
        const barraStyle = getComputedStyle(barra);
        const bottomPx = parseFloat(barraStyle.bottom || "0");
        const folgaSuperior = 8;
        const alturaMax = medidor.clientHeight - bottomPx - folgaSuperior;
        return Math.max(0, alturaMax);
    }

    function scaledValueToTickIndex(scaled) {
        for (let i = 0; i < thresholdsScaled.length; i++) {
            if (scaled <= thresholdsScaled[i]) return i;
        }
        return N_TICKS - 1;
    }

    function tickIndexToHeightPx(tickIndex) {
        const alturaMax = calcularAlturaMaximaBarra();
        const altura = Math.round(((tickIndex) / N_TICKS) * alturaMax);
        return altura;
    }

    async function iniciarGravacao() {
        resetarInterface();
        btnGritar.disabled = true;
        btnGritar.textContent = "GRAVANDO...";
        contador.textContent = RECORD_SECONDS + "s";

        try {
            streamRef = await navigator.mediaDevices.getUserMedia({ audio: true });
        } catch (err) {
            alert("Microfone não autorizado.");
            btnGritar.disabled = false;
            btnGritar.textContent = "GRITAR";
            return;
        }

        audioContext = new (window.AudioContext || window.webkitAudioContext)();
        sourceNode = audioContext.createMediaStreamSource(streamRef);
        analyser = audioContext.createAnalyser();
        analyser.fftSize = 2048;
        sourceNode.connect(analyser);

        const dataArray = new Float32Array(analyser.fftSize);

        function loop() {
            analyser.getFloatTimeDomainData(dataArray);
            const rms = calcularRMS(dataArray);
            const scaled = Math.round(rms * SCALE);
            const currentTick = scaledValueToTickIndex(scaled);

            if (currentTick > peakTick) {
                peakTick = currentTick;
                barra.style.height = tickIndexToHeightPx(peakTick) + "px";
            }
            rafId = requestAnimationFrame(loop);
        }
        loop();

        let tempo = RECORD_SECONDS;
        const intervalo = setInterval(() => {
            tempo--;
            contador.textContent = tempo > 0 ? tempo + "s" : "";
        }, 1000);

        await new Promise(resolve => setTimeout(resolve, RECORD_SECONDS * 1000));

        clearInterval(intervalo);
        if (rafId) cancelAnimationFrame(rafId);
        if (streamRef) streamRef.getTracks().forEach(t => t.stop());
        try { if (audioContext) await audioContext.close(); } catch(e){}

        exibirResultadoFinal();
    }

    function exibirResultadoFinal() {
        const resultado = LEVELS[peakTick];

        nivelTitulo.textContent = resultado.label;
        resultadoT.textContent = "RESULTADO"

        const imgEl = document.getElementById(resultado.img);
        if (imgEl) imgEl.style.display = 'block';

        if (peakTick >= 2) {
            btnGritar.textContent = "ENTRAR";
            mensagem.textContent = "Parabéns! Você foi aprovado para a Monsters University.";
        } else {
            btnGritar.textContent = "TENTAR NOVAMENTE";
            mensagem.textContent = "Você ainda não atingiu o potencial necessário. Tente de novo!";
        }

        btnGritar.disabled = false;
        contador.textContent = "";
    }

    btnGritar.addEventListener('click', () => {
        if (btnGritar.textContent === "ENTRAR") {
            window.location.href = "${pageContext.request.contextPath}/ativar-matricula";
        } else {
            iniciarGravacao();
        }
    });

    document.addEventListener('DOMContentLoaded', resetarInterface);
</script>
</body>
</html>