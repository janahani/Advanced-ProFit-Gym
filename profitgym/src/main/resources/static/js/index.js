
window.addEventListener("scroll", function () {
  var navbar = document.querySelector(".navbar");
  if (window.scrollY > 50) {
    navbar.classList.remove("navbar-dark");
    navbar.classList.add("navbar-light");
  } else {
    navbar.classList.remove("navbar-light");
    navbar.classList.add("navbar-dark");
  }
});



function handleScroll() {
  const elements = document.querySelectorAll('.animate-on-scroll');
  elements.forEach((element) => {
      const elementPosition = element.getBoundingClientRect();
      if (elementPosition.top <= window.innerHeight - 100) {
          element.classList.add('animate');
      }
  });
}

window.addEventListener('scroll', handleScroll);

