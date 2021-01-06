//构建前端控制层
app.controller("brandController",function ($scope,$controller,brandService) {
    $controller("baseController",{$scope:$scope});

    $scope.findAll=function () {
        brandService.findAll().success(function (response) {
            $scope.list=response;
        })
    }

    $scope.findPage=function(page,rows){
        brandService.findPage(page,rows).success(function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //条件查询分页
    $scope.search=function(page,rows){
        brandService.search(page,rows,$scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                //定义总记录数
                $scope.paginationConf.totalItems= response.total;
            }
        )
    }

    //查询实体
    $scope.findOne=function(id){
        brandService.findOne(id).success(
            function(response){
                $scope.entity=response;
            }
        );
    }

    //保存
    $scope.save=function () {
        /*var methodName = 'addTbBrand';
        if ($scope.entity.id != null) {//如果有ID
            methodName = 'updateTbBrand';//则执行修改方法
        }
        brandService.save().success(
                function (response) {
                    if (response.success) {
                        //重新查询
                        $scope.reloadList();//重新加载
                    } else {
                        alert(response.message);
                    }
                }
        );*/
        var object = null;
        if($scope.entity.id!=null){
            object=brandService.updateTbBrand($scope.entity);
        }else{
            object=brandService.addTbBrand($scope.entity);
        }
        object.success(function (response) {
            if (response.success) {
                //重新查询
                $scope.reloadList();//重新加载
            } else {
                alert(response.message);
            }
        })


        /*if($scope.entity.id!=null){//如果有ID
            methodName='update';//则执行修改方法
        }
        $http.post('../brand/'+ methodName +'.do',$scope.entity ).success(
                function(response){
                    if(response.success){
                        //重新查询
                        $scope.reloadList();//重新加载
                    }else{
                        alert(response.message);
                    }
                }
        );*/
    }
    //删除
    $scope.dele = function () {
        //出现过删除无效的问题 原因处在这：点击复选框时并未将该id加入复选框
        if($scope.selectIds.length==0){
            alert("请选择");
            return;
        }
        //获取选中的复选框
        brandService.dele($scope.selectIds).success(function (response) {
            if (response.success) {
                $scope.reloadList();//重新加载
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }


})
