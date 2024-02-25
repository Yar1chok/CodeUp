function addGlitchEffect(element, level) {
    var curLevel = document.getElementById('curLevel').value;
    if (curLevel < level) {
        element.classList.add("cyber-glitch-1");
    }
}

function removeGlitchEffect(element, level) {
    var curLevel = document.getElementById('curLevel').value;
    if (curLevel < level) {
        element.classList.remove("cyber-glitch-1");
    }
}


// Обработка наведения на локации в интерактивной карте блоков с темами
document.addEventListener("DOMContentLoaded", function() {
    var SvgTopic1 = document.getElementById("SvgTopic1");
    var SvgTopic2 = document.getElementById("SvgTopic2");
    var SvgTopic3 = document.getElementById("SvgTopic3");
    var SvgTopic4 = document.getElementById("SvgTopic4");
    var SvgTopic5 = document.getElementById("SvgTopic5");
    var SvgTopic6 = document.getElementById("SvgTopic6");
    var SvgTopic7 = document.getElementById("SvgTopic7");
    var SvgTopic8 = document.getElementById("SvgTopic8");
    var SvgTopic9 = document.getElementById("SvgTopic9");
    var coin1 = document.querySelector(".coin1");
    var coin2 = document.querySelector(".coin2");
    var coin3 = document.querySelector(".coin3");
    var coin4 = document.querySelector(".coin4");
    var coin5 = document.querySelector(".coin5");
    var coin6 = document.querySelector(".coin6");
    var coin7 = document.querySelector(".coin7");
    var coin8 = document.querySelector(".coin8");
    var coin9 = document.querySelector(".coin9");
    var curLevel = document.getElementById('curLevel').value;

    SvgTopic1.addEventListener("mouseenter", function() {
        coin1.classList.add("coin_hover", "coin1_hover");
        SvgTopic1.classList.add("part_hover");
        addGlitchEffect(SvgTopic1, 1);
    });

    SvgTopic1.addEventListener("mouseleave", function() {
        coin1.classList.remove("coin_hover", "coin1_hover");
        SvgTopic1.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic1, 1);
    });

    SvgTopic2.addEventListener("mouseenter", function() {
        coin2.classList.add("coin_hover", "coin2_hover");
        SvgTopic2.classList.add("part_hover");
        addGlitchEffect(SvgTopic2, 2);
    });

    SvgTopic2.addEventListener("mouseleave", function() {
        coin2.classList.remove("coin_hover", "coin2_hover");
        SvgTopic2.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic2, 2);
    });

    SvgTopic3.addEventListener("mouseenter", function() {
        coin3.classList.add("coin_hover", "coin3_hover");
        SvgTopic3.classList.add("part_hover");
        addGlitchEffect(SvgTopic3, 3);
    });

    SvgTopic3.addEventListener("mouseleave", function() {
        coin3.classList.remove("coin_hover", "coin3_hover");
        SvgTopic3.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic3, 3);
    });

    SvgTopic4.addEventListener("mouseenter", function() {
        coin4.classList.add("coin_hover", "coin4_hover");
        SvgTopic4.classList.add("part_hover");
        addGlitchEffect(SvgTopic4, 4);
    });

    SvgTopic4.addEventListener("mouseleave", function() {
        coin4.classList.remove("coin_hover", "coin4_hover");
        SvgTopic4.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic4, 4);
    });

    SvgTopic5.addEventListener("mouseenter", function() {
        coin5.classList.add("coin_hover", "coin5_hover");
        SvgTopic5.classList.add("part_hover");
        addGlitchEffect(SvgTopic5, 5);
    });

    SvgTopic5.addEventListener("mouseleave", function() {
        coin5.classList.remove("coin_hover", "coin5_hover");
        SvgTopic5.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic5, 5);
    });

    SvgTopic6.addEventListener("mouseenter", function() {
        coin6.classList.add("coin_hover", "coin6_hover");
        SvgTopic6.classList.add("part_hover");
        addGlitchEffect(SvgTopic6, 6);
    });

    SvgTopic6.addEventListener("mouseleave", function() {
        coin6.classList.remove("coin_hover", "coin6_hover");
        SvgTopic6.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic6, 6);
    });

    SvgTopic7.addEventListener("mouseenter", function() {
        coin7.classList.add("coin_hover", "coin7_hover");
        SvgTopic7.classList.add("part_hover");
        addGlitchEffect(SvgTopic7, 7);
    });

    SvgTopic7.addEventListener("mouseleave", function() {
        coin7.classList.remove("coin_hover", "coin7_hover");
        SvgTopic7.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic7, 7);
    });

    SvgTopic8.addEventListener("mouseenter", function() {
        coin8.classList.add("coin_hover", "coin8_hover");
        SvgTopic8.classList.add("part_hover");
        addGlitchEffect(SvgTopic8, 8);
    });

    SvgTopic8.addEventListener("mouseleave", function() {
        coin8.classList.remove("coin_hover", "coin8_hover");
        SvgTopic8.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic8, 8);
    });

    SvgTopic9.addEventListener("mouseenter", function() {
        coin9.classList.add("coin_hover", "coin9_hover");
        SvgTopic9.classList.add("part_hover");
        addGlitchEffect(SvgTopic9, 9);
    });

    SvgTopic9.addEventListener("mouseleave", function() {
        coin9.classList.remove("coin_hover", "coin9_hover");
        SvgTopic9.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic9, 9);
    });

    coin1.addEventListener("mouseenter", function() {
        coin1.classList.add("coin_hover", "coin1_hover");
        SvgTopic1.classList.add("part_hover");
        addGlitchEffect(SvgTopic1, 1);
    });

    coin1.addEventListener("mouseleave", function() {
        coin1.classList.remove("coin_hover", "coin1_hover");
        SvgTopic1.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic1, 1);
    });

    coin2.addEventListener("mouseenter", function() {
        coin2.classList.add("coin_hover", "coin2_hover");
        SvgTopic2.classList.add("part_hover");
        addGlitchEffect(SvgTopic2, 2);
    });

    coin2.addEventListener("mouseleave", function() {
        coin2.classList.remove("coin_hover", "coin2_hover");
        SvgTopic2.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic2, 2);
    });

    coin3.addEventListener("mouseenter", function() {
        coin3.classList.add("coin_hover", "coin3_hover");
        SvgTopic3.classList.add("part_hover");
        addGlitchEffect(SvgTopic3, 3);
    });

    coin3.addEventListener("mouseleave", function() {
        coin3.classList.remove("coin_hover", "coin3_hover");
        SvgTopic3.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic3, 3);
    });

    coin4.addEventListener("mouseenter", function() {
        coin4.classList.add("coin_hover", "coin4_hover");
        SvgTopic4.classList.add("part_hover");
        addGlitchEffect(SvgTopic4, 4);
    });

    coin4.addEventListener("mouseleave", function() {
        coin4.classList.remove("coin_hover", "coin4_hover");
        SvgTopic4.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic4, 4);
    });

    coin5.addEventListener("mouseenter", function() {
        coin5.classList.add("coin_hover", "coin5_hover");
        SvgTopic5.classList.add("part_hover");
        addGlitchEffect(SvgTopic5, 5);
    });

    coin5.addEventListener("mouseleave", function() {
        coin5.classList.remove("coin_hover", "coin5_hover");
        SvgTopic5.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic5, 5);
    });

    coin6.addEventListener("mouseenter", function() {
        coin6.classList.add("coin_hover", "coin6_hover");
        SvgTopic6.classList.add("part_hover");
        addGlitchEffect(SvgTopic6, 6);
    });

    coin6.addEventListener("mouseleave", function() {
        coin6.classList.remove("coin_hover", "coin6_hover");
        SvgTopic6.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic6, 6);
    });

    coin7.addEventListener("mouseenter", function() {
        coin7.classList.add("coin_hover", "coin7_hover");
        SvgTopic7.classList.add("part_hover");
        addGlitchEffect(SvgTopic7, 7);
    });

    coin7.addEventListener("mouseleave", function() {
        coin7.classList.remove("coin_hover", "coin7_hover");
        SvgTopic7.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic7, 7);
    });

    coin8.addEventListener("mouseenter", function() {
        coin8.classList.add("coin_hover", "coin8_hover");
        SvgTopic8.classList.add("part_hover");
        addGlitchEffect(SvgTopic8, 8);
    });

    coin8.addEventListener("mouseleave", function() {
        coin8.classList.remove("coin_hover", "coin8_hover");
        SvgTopic8.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic8, 8);
    });

    coin9.addEventListener("mouseenter", function() {
        coin9.classList.add("coin_hover", "coin9_hover");
        SvgTopic9.classList.add("part_hover");
        addGlitchEffect(SvgTopic9, 9);
    });

    coin9.addEventListener("mouseleave", function() {
        coin9.classList.remove("coin_hover", "coin9_hover");
        SvgTopic9.classList.remove("part_hover");
        removeGlitchEffect(SvgTopic9, 9);
    });
});

document.addEventListener("DOMContentLoaded", function() {
    var flash = document.getElementById("flash");
    setInterval(function() {
        flash.classList.add("part_opacity_end")
      setTimeout(function() {
        flash.classList.remove("part_opacity_end")
      }, 2000); // измените это значение, чтобы управлять длительностью вспышки
    }, 5000); // интервал вспышки в миллисекундах (в данном случае каждые 5 секунд)
  });

document.addEventListener("DOMContentLoaded", function() {
    var flash = document.getElementById("flash_red");
    setInterval(function() {
        flash.classList.add("part_opacity_end_red")
      setTimeout(function() {
        flash.classList.remove("part_opacity_end_red")
      }, 1500);
    }, 4000);
  });

document.addEventListener("DOMContentLoaded", function() {
    var flash = document.getElementById("flash_red_1");
    setInterval(function() {
        flash.classList.add("part_opacity_end_red_1")
      setTimeout(function() {
        flash.classList.remove("part_opacity_end_red_1")
      }, 1000);
    }, 3000);
  });

document.addEventListener("DOMContentLoaded", function() {
    var flash = document.getElementById("flash_bg");
    setInterval(function() {
        flash.classList.add("part_opacity_end")
      setTimeout(function() {
        flash.classList.remove("part_opacity_end")
      }, 1000);
    }, 4000);
  });
