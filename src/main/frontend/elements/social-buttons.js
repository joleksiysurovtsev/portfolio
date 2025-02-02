window.initializeSocialButtons = function (container) {
    console.log("Инициализация social buttons");

    if (!container) {
        console.error("Контейнер не найден!");
        return;
    }

    const toggle = container.querySelector(".open-btn");
    const socialBtns = container.querySelectorAll(".social-btn");

    if (!toggle || socialBtns.length === 0) {
        console.error("Кнопки соцсетей не найдены!");
        return;
    }

    let delay = 200;
    let open = false;
    let curStep = 0;
    const totalSteps = socialBtns.length;

    function setStep() {
        container.classList.remove("step-0", "step-1", "step-2", "step-3");
        container.classList.add(`step-${curStep}`);
    }

    let lastTimeout;

    function animate() {
        if (curStep >= totalSteps) {
            curStep = 0;
            return;
        }
        open = true;
        setStep();
        curStep++;
        lastTimeout = setTimeout(animate, delay);
    }

    toggle.addEventListener("click", function () {
        console.log("Клик по кнопке, open = ", open);
        if (!open) {
            animate();
        } else {
            container.classList.remove("step-0", "step-1", "step-2", "step-3");
            clearTimeout(lastTimeout);
            open = false;
            curStep = 0;
        }
    });

    console.log("Кнопки соцсетей успешно инициализированы!");
};