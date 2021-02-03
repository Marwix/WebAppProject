
var prepareButtons = () => {
    var PurchaseButton = document.querySelector('.purchase-button');
    var cartNotification = document.querySelector('TopBar .cart-button .cart-notification');
    var cartText = document.querySelector('TopBar .cart-button .cart-notification h6');
    var count = parseInt(localStorage.getItem(PurchaseButton.id));
    var cart = localStorage.getItem('Cart');
    
    var setCartNotification = () => {
        if (getUnique() > 0) {
            cartNotification.style.backgroundColor="#49FF00";
            cartText.innerText = getUnique();
        };
    }
    
    setCartNotification();

    PurchaseButton.addEventListener('click', (e) => {
        
        if (isNaN(count)) {
            count = 0;
        }

        // var products = {
        //     'catapultValue' : {'name' : fisk, 'option': 'blue', 'price':40},
        //     'tankValue' : {'name': pangpang, 'option':'standard', 'price':500}
        // }
        
        
        var color = sessionStorage.getItem("Color");
        console.log(color);
        count += 1;
        localStorage.setItem(PurchaseButton.id, count);
        setCartNotification();
    })
    

}

var getUnique = () => {
    
     var unique = 0;
     unique += parseInt(localStorage.getItem('catapultValue', 10)) > 0 ? 1 : 0; 
     unique += parseInt(localStorage.getItem('tankValue', 10)) > 0 ? 1 : 0; 
     unique += parseInt(localStorage.getItem('planeValue', 10)) > 0 ? 1 : 0; 
    return unique;
}

var setCartNotification = () => {
    var cartNotification = document.querySelector('TopBar .cart-button .cart-notification');
    var cartText = document.querySelector('TopBar .cart-button .cart-notification h6');
    if (getUnique() > 0) {
        cartNotification.style.backgroundColor="#49FF00";
        cartText.innerText = getUnique();
    };
}

    






