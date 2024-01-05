function updateForm() {
    location.href = "commUpdateForm?trNo=${post.trNo}"
}

function deletePost() {
    var check = confirm('글을 삭제하시겠습니까?');

    if (check) {
        location.href = "deletePost?trNo=${post.trNo}"
        alert('글이 삭제되었습니다.');
    } else {

    }
}