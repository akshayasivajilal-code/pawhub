
window.addEventListener("load", () => {

 
    const splash = document.querySelector(".splash-screen");

    setTimeout(() => {

        splash.style.opacity = "0";
        splash.style.transform = "scale(1.05)";
        splash.style.transition = "all 0.10s ease";

    }, 1800);

   
    setTimeout(() => {

        window.location.href = "home.html";

    }, 2600);

});

document.addEventListener("keydown", (event) => {

    if (event.key === "Enter") {

        window.location.href = "home.html";

    }

});

document.addEventListener("click", () => {

    window.location.href = "home.html";

});

document.addEventListener("click",() => {

    window.location.href = "login.html";
{
    setTimeout(function () {
        window.location.href = "login.html";
    }, 3000); 

}  
}); 
