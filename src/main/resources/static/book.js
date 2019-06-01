function f_delete(id) {
    const formData = new FormData()
    formData.append('id', id)
    return fetch('/books/'+id, {
        method: 'delete',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:  JSON.stringify({id: id})
    }).then(function(response) {
        window.location.reload(false);
        return response.json();});

}

function f_edit(id) {
    localStorage.setItem("book_id", id);
}

function f_save() {
    var id =  document.getElementById("id-input").value==""?"0":document.getElementById("id-input").value;
    var name = !document.getElementById("name-edit")?document.getElementById("name-new").value:document.getElementById("name-edit").value;
    var author_id = document.getElementById("author_id").value==""?"0":document.getElementById("author_id").value;
    var genre_id = document.getElementById("genre_id").value==""?"0":document.getElementById("genre_id").value;
    return fetch('/books', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:  JSON.stringify({id:parseInt(id),name:name.toString(), authorID: parseInt(author_id),genreID: parseInt(genre_id)})
    }).then(function(response) {
        window.location.replace("books.html");
        return response.json();});
}

function getauthors (author_id){
    return fetch('/authors/' + author_id, {
        method: 'put',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id: id})
    })
        .then(response => response.json())
        .then(author => {
            new Vue({
                el: '#author'
                , data: {
                    author: author
                }
            })
        })


}