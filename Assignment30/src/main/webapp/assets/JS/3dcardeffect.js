function getOffset(el) {
    const rect = el.getBoundingClientRect();
    
}

//Movement Anim

const allCards = document.querySelectorAll('.ui-dataview-column img');
const container = document.querySelector('.card');

const stars = document.querySelectorAll('.ui-rating-star');
const productName = document.querySelectorAll('.product-name');
const productDescription = document.querySelectorAll('.product-description');
const productPrice = document.querySelectorAll('.product-price');
//Moving event

container.addEventListener('mousemove', (e)=> {
    for (var i = 0; i<allCards.length; i++){
        let xAxis = ((allCards[i].getBoundingClientRect().left) - e.pageX)/20;
        let yAxis = ((allCards[i].getBoundingClientRect().top) - e.pageY)/20;
        if (xAxis > 20) xAxis = 20; 
        if (xAxis < -20) xAxis = -20; 
        if (yAxis > 20) yAxis = 20; 
        if (yAxis < -20) yAxis = -20;
        allCards[i].style.transform =`rotateY(${-xAxis}deg) rotateX(${yAxis}deg)`
    }
    container.style.perspective="2000px";
})

container.addEventListener('mouseleave', (e)=> {
    for (var i = 0; i < allCards.length; i++) {
        allCards[i].style.transition = 'all 0.5s ease';
        allCards[i].style.transform = `rotateY(0deg) rotateX(0deg)`;
    }

    //Reset pop out
    for (var i = 0; i<allCards.length; i++){
        allCards[i].style.transform = 'translateZ(0px)';
    }
})

container.addEventListener('mouseenter', (e)=> {
    for (var i = 0; i < allCards.length; i++) {
        allCards[i].style.transition = "none";
    }

    //Pop out
    for (var i = 0; i<allCards.length; i++){
        allCards[i].style.transform = 'translateZ(200px)';
    }
})