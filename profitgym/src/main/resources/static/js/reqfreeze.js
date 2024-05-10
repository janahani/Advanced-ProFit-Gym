// freeze/unfreeze modal admin/employee side
function handleDateChange(modalID) {
  var selectedDate = $("#datepicker-" + modalID).val();
  var modalLabel = document
    .getElementById("modal-" + modalID)
    .querySelector(".confirmation-text");
  if (selectedDate) {
    modalLabel.textContent =
      "Are you sure you want to freeze your membership to " +
      selectedDate +
      "?";
  }
}

function showModal(modalID) {
  console.log("Modal ID: " + modalID);
  $("#modal-" + modalID).fadeIn();
}
function closeModal(modalID) {
  $("#modal-" + modalID).fadeOut();
}

// freeze/unfreeze modal user side
var freezebtn = document.getElementById("freeze-button");
var datePicker = document.getElementById("datepicker");
function showUnfreezeModal() {
  var modalLabel = document.getElementById("confirmation-text");
  modalLabel.textContent = "Are you sure you want to unfreeze your membership?";
  $("#userProfileModal").fadeIn();
}

function openModal() {
  var selectedDate = document.getElementById("datepicker").value;
  var modalLabel = document.getElementById("confirmation-text");

  if (selectedDate) {
    modalLabel.textContent =
      "Are you sure you want to freeze your membership to " +
      selectedDate +
      "?";
    $("#userProfileModal").fadeIn();
  } else {
    return;
  }
}
function fadeOutModal() {
  $("#userProfileModal").fadeOut();
}
function showUnfreezeModal() {
  document.getElementById("confirmation-text").textContent =
    "Are you sure you want to unfreeze your membership?";
  $("#userProfileModal").fadeIn();
}
