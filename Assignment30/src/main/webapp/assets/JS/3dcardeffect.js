//Movement Anim

const card = document.querySelector('.card');
const container = document.querySelector('.card-area');

const catapult = document.querySelector('.product-3d img');
const star1 = document.querySelector('#star1');
const star2 = document.querySelector('#star2');
const star3 = document.querySelector('#star3');
const star4 = document.querySelector('#star4');
const star5 = document.querySelector('#star5');
//Moving event

container.addEventListener('mousemove', (e)=> {
    let xAxis = ((window.innerWidth/2) - e.pageX)/20;
    let yAxis = ((window.innerHeight/2) - e.pageY)/20;
    if (xAxis > 20) xAxis = 20; 
    if (xAxis < -20) xAxis = -20; 
    if (yAxis > 20) yAxis = 20; 
    if (yAxis < -20) yAxis = -20; 
    card.style.transform =`rotateY(${-xAxis}deg) rotateX(${yAxis}deg)`
})

container.addEventListener('mouseleave', (e)=> {
    card.style.transition = 'all 0.5s ease';
    card.style.transform = `rotateY(0deg) rotateX(0deg)`;

    //Reset pop out
    catapult.style.transform = 'translateZ(0px)';
    star1.style.transform = 'translateZ(0px)';
    star2.style.transform = 'translateZ(0px)';
    star3.style.transform = 'translateZ(0px)';
    star4.style.transform = 'translateZ(0px)';
    star5.style.transform = 'translateZ(0px)';
})

container.addEventListener('mouseenter', (e)=> {
    card.style.transition = "none";

    //Pop out
    catapult.style.transform = 'translateZ(200px)';
    star1.style.transform = 'translateZ(25px)';
    star2.style.transform = 'translateZ(50px)';
    star3.style.transform = 'translateZ(75px)';
    star4.style.transform = 'translateZ(100px)';
    star5.style.transform = 'translateZ(125px)';
})