// js/script.js

document.addEventListener('DOMContentLoaded', function () {
    const explorarBtn = document.querySelector('a[href="#programas"].btn-primary');

    if (explorarBtn) {
        explorarBtn.addEventListener('click', function (e) {
            e.preventDefault();

            const targetSection = document.querySelector('#programas');
            if (!targetSection) return;

            // Obtener altura del navbar fijo
            const navbar = document.querySelector('.sticky-top');
            const navbarHeight = navbar ? navbar.offsetHeight : 0;

            // Calcular posición destino
            const targetPosition = targetSection.getBoundingClientRect().top + window.scrollY - navbarHeight;
            const startPosition = window.scrollY;
            const distance = targetPosition - startPosition;

            // DURACIÓN DE LA ANIMACIÓN (en milisegundos)
            // 1500 = 1.5 segundos (más lento y notorio)
            const duration = 1500;

            let startTime = null;

            // Función de easing para movimiento más natural
            function easeInOutCubic(t) {
                return t < 0.5
                    ? 4 * t * t * t
                    : 1 - Math.pow(-2 * t + 2, 3) / 2;
            }

            function animation(currentTime) {
                if (startTime === null) startTime = currentTime;

                const timeElapsed = currentTime - startTime;
                let progress = Math.min(timeElapsed / duration, 1);

                // Aplicar easing para movimiento suave
                const easeProgress = easeInOutCubic(progress);

                // Mover el scroll
                window.scrollTo(0, startPosition + (distance * easeProgress));

                // Continuar animación si no ha terminado
                if (progress < 1) {
                    requestAnimationFrame(animation);
                }
            }

            // Iniciar animación
            requestAnimationFrame(animation);
        });
    }
});


// js/script.js - Versión con rebote
/*
document.addEventListener('DOMContentLoaded', function() {
    const explorarBtn = document.querySelector('a[href="#programas"].btn-primary');
    
    if (explorarBtn) {
        explorarBtn.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetSection = document.querySelector('#programas');
            if (!targetSection) return;
            
            const navbar = document.querySelector('.sticky-top');
            const navbarHeight = navbar ? navbar.offsetHeight : 0;
            
            const targetPosition = targetSection.getBoundingClientRect().top + window.scrollY - navbarHeight;
            const startPosition = window.scrollY;
            const distance = targetPosition - startPosition;
            const duration = 1200; // 1.2 segundos
            
            let startTime = null;
            
            // Easing con rebote (sobrepasa un poco y vuelve)
            function easeOutBounce(t) {
                const n1 = 7.5625;
                const d1 = 2.75;
                
                if (t < 1 / d1) {
                    return n1 * t * t;
                } else if (t < 2 / d1) {
                    return n1 * (t -= 1.5 / d1) * t + 0.75;
                } else if (t < 2.5 / d1) {
                    return n1 * (t -= 2.25 / d1) * t + 0.9375;
                } else {
                    return n1 * (t -= 2.625 / d1) * t + 0.984375;
                }
            }
            
            function animation(currentTime) {
                if (startTime === null) startTime = currentTime;
                
                const timeElapsed = currentTime - startTime;
                let progress = Math.min(timeElapsed / duration, 1);
                
                // Aplicar easing con rebote
                const easeProgress = easeOutBounce(progress);
                
                window.scrollTo(0, startPosition + (distance * easeProgress));
                
                if (progress < 1) {
                    requestAnimationFrame(animation);
                }
            }
            
            requestAnimationFrame(animation);
        });
    }
}); */