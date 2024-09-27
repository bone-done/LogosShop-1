let port = 8080;
// let host = `http://localhost:${port}/`
let host = `https://logos-shop.herokuapp.com/`
let url = `${host}api/`;

class Card {
  constructor(title, description, price, id) {
    this.title = title;
    this.description = description;
    this.price = price;
    this.id = id;
    return this.generateHTMLElement();
  }

  generateHTMLElement() {
    let cardElement = document.createElement("div");
    let columnElement = document.createElement("div");
    let columnElement2 = document.createElement("div");
    let imageOrderElement = document.createElement("img");
    let productImageDivEl = document.createElement("div");
    let productImageEl = document.createElement("img");
    let miniHeaderEl = document.createElement("div");
    let titleEl = document.createElement("div");
    let priceEl = document.createElement("div");
    let descriptionEl = document.createElement("div");

    // COLUMN 1
    cardElement.className = "card";
    cardElement.id = "cardId-" + this.id;

    // COLUMN 2
    columnElement.className = "column";
    cardElement.appendChild(columnElement);
    columnElement2.className = "column";
    cardElement.appendChild(columnElement2);

    // IMAGE
    imageOrderElement.id = "order-image";
    imageOrderElement.setAttribute(
      "src",
      "https://www.clipartmax.com/png/small/119-1192927_order-icon-round-png.png"
    );
    imageOrderElement.setAttribute("alt", "order");
    cardElement.appendChild(imageOrderElement);

    // COLUMN 1 IMAGE
    productImageDivEl.className = "image";

    productImageEl.setAttribute("src", "");
    productImageEl.className = "img";
    productImageDivEl.appendChild(productImageEl);

    columnElement.appendChild(productImageDivEl);

    // COLUMN 2 INFO
    miniHeaderEl.className = "mini-header";
    titleEl.className = "title";
    titleEl.innerText = this.title;
    priceEl.className = "price";
    priceEl.innerText = "грн. " + this.price;
    miniHeaderEl.appendChild(titleEl);
    miniHeaderEl.appendChild(priceEl);
    descriptionEl.className = "desc";
    descriptionEl.innerText = this.description;

    columnElement2.appendChild(miniHeaderEl);
    columnElement2.appendChild(descriptionEl);

    return cardElement;
  }
}

document.getElementById("login").addEventListener("click", () => {
  console.log("debug");
  window.location.href = "/LogosShop/front/login.html";
});

let mainEl = document.getElementsByTagName("main")[0];
// mainEl.appendChild(new Card("Test", "Test", 1, 1))

fetch(url + "product")
  .then((resp) => resp.json())
  .then((page) => {
    page.content.forEach((product) => {
      mainEl.appendChild(
        new Card(product.title, product.description, product.price, product.id)
      );

      let orderImageEl = document.querySelector(
        `#cardId-${product.id} #order-image`
      );
      orderImageEl.addEventListener("click", () => {
        fetch(url + `product/${product.id}`, {
          method: "PUT",
          headers: {
            Authorization: `Basic ${btoa(
              window.sessionStorage.getItem("email") +
                ":" +
                window.sessionStorage.getItem("password")
            )}`
          }
        })
            .then(resp => resp.json())
            .then(prod => {
                console.log(prod)
            })
      });

      let imageEl = document.querySelector(`#cardId-${product.id} div div img`);
      fetch(url + `image/byProductId/${product.id}`)
        .then((resp) => resp.json())
        .then((imageEntity) => {
          let fileName = imageEntity.fileName;
          imageEl.setAttribute("src", url + "image/file/" + fileName);
        });
    });
  });
