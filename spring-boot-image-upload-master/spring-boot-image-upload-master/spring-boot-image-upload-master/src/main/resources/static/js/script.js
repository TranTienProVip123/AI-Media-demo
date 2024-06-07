function filterFiles(type) {
  $(".file-item").hide();
  if (type === 'all') {
    $(".file-item").show();
  } else {
    $("." + type).show();
  }
}

$(document).ready(function () {
  $(".btn-delete").on("click", function (e) {
    e.preventDefault();
    link = $(this);

    fileName = link.attr("fileName");
    $("#yesBtn").attr("href", link.attr("href"));
    $("#confirmText").html("Do you want to delete the File: <strong>" + fileName + "</strong>?");
    $("#confirmModal").modal();
  });
});
document.addEventListener("DOMContentLoaded", function() {
  // Load directory tree and files when page is loaded
  loadDirectoryTree();
  loadFiles();

  // Add event listeners for interaction
  // Example: Double click to open directory or view image/video
  // Example: Single click to select file
});

function loadDirectoryTree() {
  // AJAX call to backend API to fetch directory tree
  // Populate directory tree in HTML dynamically
}

function loadFiles() {
  // AJAX call to backend API to fetch files in current directory
  // Populate files in file viewer area dynamically
}

function openDirectory(directory) {
  // AJAX call to backend API to change current directory
  // Update directory tree and file viewer accordingly
}

function viewFile(file) {
  // Display selected image/video in file preview area
}

function selectFile(file) {
  // Highlight selected file and display file information
}

function downloadFile(file) {
  // Download selected file
}

// Add more functions for interactions like delete, move, etc.
document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.btn.delete');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const url = button.getAttribute('data-url');
            const fileName = button.getAttribute('data-file-name');

            if (confirm(`Do you want to delete the image: ${fileName}?`)) {
                window.location.href = url;
            }
        });
    });
});
$(document).ready(function () {
    $(".btn.delete").on("click", function (e) {
        e.preventDefault();
        let link = $(this);
        let fileName = link.attr("data-file-name");
        $("#yesBtn").attr("href", link.attr("data-url"));
        $("#confirmText").html("Do you want to delete the Image: <strong>" + fileName + "</strong>?");
        $("#confirmModal").modal();
    });
});


