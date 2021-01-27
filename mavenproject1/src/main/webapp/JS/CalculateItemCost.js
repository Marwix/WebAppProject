function updateItemPrice(value) 
{
    
    var items = document.querySelectorAll(".title .item-input-value");
    var listprices = document.querySelectorAll('.price .price_total');
 
    let result = 0;
    for (let i = 0; i < items.length; i++)
    {
        result = listprices[i].dataset['price'] * items[i].value;
        listprices[i].textContent = '$' + result;
    }

    console.log(result);

}