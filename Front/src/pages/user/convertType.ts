export const convertType = (type: number) => {
  console.log(type, '나와바')
  let typeName
  switch (type) {
  case 1:
    typeName = '신입'
      break;
  case 2:
    typeName = '사원'
      break;
  case 3:
    typeName = '팀장'
      break;
  case 4:
    typeName = '관리자'
      break;
  }

  return typeName
}
