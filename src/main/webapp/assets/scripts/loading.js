function criarTela() {
    const existente = document.getElementById("tela-carregamento");
    if (existente) existente.remove();

    const t = document.createElement("div");
    t.id = "tela-carregamento";

    t.style.cssText = `
        position: fixed;
        inset: 0;
        background: #0d1b3e;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 2rem;
        z-index: 999999;
        opacity: 1;
        transition: opacity 0.5s ease;
    `;

    t.innerHTML = `
        <p style="font-size:1.5rem;letter-spacing:.3em;color:#82CDFF;text-transform:uppercase;font-family:sans-serif;opacity:0.8;margin:0;">Monsters University</p>
        <div style="width:20rem;height:0.5rem;border-radius:3px;background:#024491;overflow:hidden;">
            <div id="barra-progresso-loading" style="height:100%;border-radius:3px;background:#82CDFF;width:0%;animation:encher 8s cubic-bezier(.4,0,.2,1) forwards;"></div>
        </div>
        <div style="display:flex;gap:0.7rem;">
            <div style="width:0.8rem;height:0.8rem;border-radius:50%;background:#82CDFF;animation:saltar 1.2s ease-in-out infinite;animation-delay:0s;opacity:.4;"></div>
            <div style="width:0.8rem;height:0.8rem;border-radius:50%;background:#82CDFF;animation:saltar 1.2s ease-in-out infinite;animation-delay:.2s;opacity:.7;"></div>
            <div style="width:0.8rem;height:0.8rem;border-radius:50%;background:#82CDFF;animation:saltar 1.2s ease-in-out infinite;animation-delay:.4s;opacity:1;"></div>
        </div>
        <style>
            @keyframes encher {
                0%   { width: 0%  }
                90%  { width: 75% }
                100% { width: 85% }
            }
            @keyframes saltar {
                0%, 80%, 100% { transform: translateY(0)    }
                40%           { transform: translateY(-7px) }
            }
        </style>
    `;

    document.body.appendChild(t);
}

criarTela();

window.addEventListener("load", function () {
    const tela = document.getElementById("tela-carregamento");
    if (!tela) return;

    const barra = document.getElementById("barra-progresso-loading");
    if (barra) {
        const larguraAtual = barra.getBoundingClientRect().width;
        const larguraPai = barra.parentElement.getBoundingClientRect().width;
        const porcentagemAtual = (larguraAtual / larguraPai) * 100;

        barra.style.animation = "none";
        barra.style.width = porcentagemAtual + "%";
        barra.offsetHeight; // força reflow
        barra.style.transition = "width 0.4s ease";
        barra.style.width = "100%";
    }

    setTimeout(() => {
        tela.style.opacity = "0";
        setTimeout(() => tela.remove(), 500);
    }, 450);

    document.querySelectorAll("form").forEach(function (form) {
        form.addEventListener("submit", function () {
            criarTela();
        });
    });
});