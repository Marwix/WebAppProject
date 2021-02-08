function getTotal() {
    var items = document.querySelectorAll('.price .price_total');

    let result = 0;
    for (let i = 0; i < items.length; i++)
    {
        var x = parseInt(items[i].innerHTML.replace("$", ""));
        result += x;
    }

    document.getElementById("total-pay-text").textContent = "TOTAL TO PAY: $" + result;
}

