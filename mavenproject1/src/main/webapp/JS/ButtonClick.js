
window.onload = function() { 
    const buttons = document.querySelectorAll('.list-group-item');
    for (const btn of buttons){
        btn.addEventListener('click', function() {
            console.log(btn.id);
        })
    }
 }