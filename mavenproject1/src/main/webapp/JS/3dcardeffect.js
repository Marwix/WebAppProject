//Movement Anim

const card = document.querySelector('.card');
const container = document.querySelector('.card-area');

// const title = document.querySelector('.info .title');
// const catapult = document.querySelector('.productimage img');
// const purchase = document.querySelector('.info .purchase button');
// const description = document.querySelector('.info h3');
// const sizes = document.querySelector('.info .sizes');
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
    card.style.transition = "all 0.5s ease";
    card.style.transform = `rotateY(0deg) rotateX(0deg)`;

    //Reset pop out
    // catapult.style.transform = 'translateZ(0px)';
    // title.style.transform = 'translateZ(0px)';
    // description.style.transform = 'translateZ(0px)';
    // sizes.style.transform = 'translateZ(0px)';
    // purchase.style.transform = 'translateZ(0px)';
})

container.addEventListener('mouseenter', (e)=> {
    card.style.transition = "none";

    //Pop out
    // catapult.style.transform = 'translateZ(200px)';
    // title.style.transform = 'translateZ(150px)';
    // description.style.transform = 'translateZ(100px)';
    // sizes.style.transform = 'translateZ(75px)';
    // purchase.style.transform = 'translateZ(40px)';
})