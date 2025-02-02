document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.querySelector("#image_file_input");
   const imagePreview = document.querySelector("#upload_image_preview");
 
   if (imageInput && imagePreview) {
     imageInput.addEventListener("change", function (event) {
       let file = event.target.files[0];
       let reader = new FileReader();
       reader.onload = function () {
         imagePreview.setAttribute("src", reader.result);
       };
       reader.readAsDataURL(file);
     });
   } else {
     console.error("Image input or preview element not found");
   }
 
   document.querySelector("form").addEventListener("reset", function () {
     imagePreview.setAttribute("src", "");
   });
 });

 function disableButton(button) {
  button.disabled = true;
  button.innerText = 'Processing...';
  button.form.submit();
}