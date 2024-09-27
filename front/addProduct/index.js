document.getElementById("create").addEventListener("click", e => {
    let port = 8080;
    // let host = `http://localhost:${port}/`
    let host = `https://logos-shop.herokuapp.com/`
    let url = `${host}api/`;


    let titleValue = document.getElementById("title").value
    let descValue = document.getElementById("description").value
    let priceValue = document.getElementById("price").value
    let formData = new FormData();
    formData.append('file', document.getElementById("file").files[0]);

    let product = {
        title: titleValue,
        description: descValue,
        price: priceValue
    }

    fetch(url + "product", {
        method: "POST",
        headers: {
            "Authorization": `Basic ${btoa(
                    window.sessionStorage.getItem('email') +
                    ":" +
                    window.sessionStorage.getItem('password')
            )}`,
            "Content-type": "application/json"
        },
        body: JSON.stringify(product)
    })
    .then(resp => resp.json())
    .then(createdProduct => {
        console.log(createdProduct)
        console.log(createdProduct.id)

        fetch(url + `image?isHeadPicture=true&productId=${createdProduct.id}`, {
            method: "POST",
            headers: {
                "Authorization": `Basic ${btoa(
                        window.sessionStorage.getItem('email') +
                        ":" +
                        window.sessionStorage.getItem('password')
                )}`,
            },
            body: formData
        })
        .then(resp => {
            if(resp.status !== 200) {
                alert("Some error")
                console.log(resp)
                return null
            }
            return resp.json()
        })
        .then(createdImage => {
            console.log(createdImage)
            window.location.href = "/LogosShop/front/index.html"
        })
    })
})