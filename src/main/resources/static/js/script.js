let currentTheme = getTheme();
console.log(currentTheme);
 

// Set the theme on page load
document.querySelector("html").classList.add(currentTheme);
changeTheme();

// Function to change the theme
function changeTheme() {
  const themeButton = document.querySelector("#theme_change");
  themeButton.addEventListener("click", (event) => {
     // Toggle the theme
    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }

    // Apply the new theme
    document
      .querySelector("html")
      .classList.remove(currentTheme === "dark" ? "light" : "dark");
    document.querySelector("html").classList.add(currentTheme);

    // Save the new theme to localStorage
    setTheme(currentTheme);

    // Update button text
    document.querySelector("#theme_change span").textContent =
      currentTheme === "dark" ? "Light" : "Dark";
  });
}

// Function to set the theme in localStorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Function to get the theme from localStorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light"; // Default to light theme if none is set
}

 

