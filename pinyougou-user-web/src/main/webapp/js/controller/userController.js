 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){
	
	//注册
	$scope.reg=function(){
		if($scope.password!=$scope.entity.password){
			alert("两次输入的密码不一样");
			return;
		}
		//非空判断
		if($scope.entity.password==null||$scope.password==null){
			alert("输入密码为空");
		}
		userService.add($scope.entity).success(
			function(response){
				alert(response.message);
			}		
		);				
	}

	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==null){
			alert("请输入手机号！");
			return ;
		}
		userService.sendCode($scope.entity.phone).success(
			function(response){
				alert(response.message);
			}
		);
	}

	//保存
	$scope.reg=function(){
		userService.add( $scope.entity, $scope.smscode  ).success(
			function(response){
				alert(response.message);
			}
		);
	}






});
