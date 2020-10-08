function deleteListener(button, type, id) {
    button.onclick = function() {
        let url = "http://localhost:1337/" + type + "/delete/" + id;
        fetch(url, {
        method: 'delete',
        headers: {
            "Content-type": "application/json"
        },
    })
    .then(function(data) {
        console.log("Delete request succeeded with response: " + data);
        //alert("Deleted");
        location.reload();
        return;
    })
    .catch(function(error) {
        console.log("Delete request failed: " + error);
        alert("Gee Whizz Batman!\nI can't delete that!");
        location.reload();
        return;
    })
    }
}