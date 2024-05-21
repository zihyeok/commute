import { BoardArticle } from '@/@api/board/index';
import { router } from './BoardModalArticle.vue';

const Article = async (val1: number) => {
const req: any = { ...reqModel.value };

// session에서 userIdx 추출
const loginUser = sessionStorage.getItem('loginUser');
if (loginUser) {
const userInfo = JSON.parse(loginUser);

req.idx = userInfo.idx;
}
else {
alert('다시 시도해주세요');
console.error('loginUser 정보가 없습니다.');
router.push('/login');
}

// -------------
try {
req.page = val1;

const result = await BoardArticle(req);

boards.value = result.data.list.content;
totalPage.value = result.data.list.totalPages;

if (result.data.res !== 'OK') {
// eslint-disable-next-line no-alert
alert(result.data.msg);
router.push('/');

return;
}
}
catch (e) {
console.error(e);
}
};
