

var purchaseOptions = () => {
    var PurchaseOptionsButtons = document.querySelectorAll('#productInfo .product-option-button');
    PurchaseOptionsButtons.forEach(button =>{
        if(button.id == 'Natural'){
            button.style.borderStyle = 'inset';
        }
    })
    PurchaseOptionsButtons.forEach(button => {
       button.addEventListener('click', (e) => {
        checkButtonStates(button.id);
        button.style.borderStyle = (button.style.borderStyle!=='inset' ? 'inset'  : 'outset');
        sessionStorage.removeItem("Color");
        if(button.style.borderStyle == 'inset'){
            sessionStorage.setItem("Color", "" + button.id);
        }
       })
   });
   
   var checkButtonStates = (except) =>{
    PurchaseOptionsButtons.forEach(button => {
        if(button.style.borderStyle==='inset' && button.id != except){
            button.style.borderStyle = 'outset'
        }
    });
   }
}