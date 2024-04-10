const modal = document.querySelector(".modal");
const buttonsOpen = document.querySelectorAll(".btn-modal");
modal.style.csstext = `
    display: flex;
    visibility: hidden;
    opacity: 0;
    transition: opacity 300ms ease-in-out;
`;

const closeModal = (event) => {
  const target = event.target;
  if (
    target === modal ||
    target.closest(".modal-cross") ||
    event.code === "Escape"
  ) {
    modal.style.opacity = 0;
    setTimeout(() => {
      modal.style.visibility = "hidden";
    }, 300);

    window.removeEventListener("keydown", closeModal);
  }
};

const openModal = () => {
  modal.style.visibility = "visible";
  modal.style.opacity = 1;
  window.addEventListener("keydown", closeModal);
};

buttonsOpen.forEach((btn) => {
  btn.addEventListener("click", openModal);
});
modal.addEventListener("click", closeModal);
