console.log("Contacts.js");


const baseURL = "http://localhost:8080";
// const baseURL = "https://www.scm20.site";
const viewContactModal = document.getElementById("view_contact_modal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    setTimeout(() => {
      contactModal.classList.add("scale-100");
    }, 50);
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_mdoal",
  override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

async function loadContactdata(id) {
  //function call to load data
  console.log(id);
  try {
    const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
    console.log(data);
    document.querySelector("#contact_name").innerHTML = data.name;
    document.querySelector("#contact_email").innerHTML = data.email;
    document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_address").innerHTML = data.address;
    document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
    document.querySelector("#contact_about").innerHTML = data.description;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favorite) {
      contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    document.querySelector("#contact_website").href = data.websiteLink || "";
    document.querySelector("#contact_website").innerHTML =
      data.websiteLink || "";
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
    checkAndDisplayMessage("contact_website", "contact_website_message");
    checkAndDisplayMessage("contact_linkedIn", "contact_linkedIn_message");
    openContactModal();
  } catch (error) {
    console.log("Error: ", error);
  }
}

// delete contact

async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    customClass: {
      confirmButton: 'bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded',
      cancelButton: 'bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded ml-2' // Add margin-left to the cancel button
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/user/contacts/delete/` + id;
      window.location.replace(url);
 
    }
  });
}

function checkAndDisplayMessage(elementId, messageId) {
  var element = document.getElementById(elementId);
  var messageElement = document.getElementById(messageId);

  if (!element || element.textContent.trim() === "") {
    element.style.display = "none";
    messageElement.style.display = "inline";
  } else {
    element.style.display = "inline";
    messageElement.style.display = "none";
  }
}

document.addEventListener("DOMContentLoaded", function() {
  handleUrlParams();
 });


function handleUrlParams() {
  const urlParams = new URLSearchParams(window.location.search);
   if ( urlParams.has('deleted')) {
      Swal.fire({
          title: 'Deleted!',
          text: 'The contact has been deleted successfully.',
          icon: 'success',
          confirmButtonText: 'OK',
          customClass: {
              confirmButton: 'bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded'
          },
          buttonsStyling: false
      });
   
  }
   
}

 
  const form = document.querySelector('form');
  const addButton = document.getElementById('add-contact-button');

  form.addEventListener('submit', function(event) {
      addButton.disabled = true;

      // Assuming the form submission is handled via AJAX
      event.preventDefault();
      const formData = new FormData(form);

      fetch(form.action, {
          method: form.method,
          body: formData
      })
      .then(response => response.json())
      .then(data => {
          // Handle success
          addButton.disabled = false;
          // Optionally, show a success message or redirect
      })
      .catch(error => {
          // Handle error
          addButton.disabled = false;
          // Optionally, show an error message
      });
  });

