//构建前端服务层
app.service("brandService",function ($http) {
    this.findAll = function () {
        return $http.  get("../brand/findAll.do");
    }
    this.findPage = function (page,rows) {
        return $http.get('../brand/findPage.do?page='+page+'&rows='+rows);
    }
    this.dele = function (selectIds) {
        return $http.get("../brand/deleteTbBrand.do?ids=" + selectIds);
    }
    this.search = function (page,rows,searchEntity) {
        return $http.post("../brand/search.do?page="+page+"&rows="+rows,searchEntity);
    }
    this.findOne = function(id){
        return $http.get('../brand/findOne.do?id='+id);
    }
    this.addTbBrand = function (entity) {
        return $http.post('../brand/addTbBrand.do',entity);
    }
    this.updateTbBrand = function (entity) {
        return $http.post('../brand/updateTbBrand.do',entity);
    }
    this.selectOptionList = function () {
        return $http.post('../brand/selectOptionList.do');
    }
})