<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title></title>

  <!-- Custom fonts for this template-->
  <link href="./admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

  <!-- Page level plugin CSS-->
  <link href="./admin/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="./admin/css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">

  <nav class="navbar navbar-expand navbar-dark bg-light static-top">

    <a class="navbar-brand mr-1" href="./Main.me">PAOSDY</a>

    <button class="btn btn-link btn-sm text-dark order-1 order-sm-0" id="sidebarToggle" href="#">
      <i class="fas fa-bars"></i>
    </button>

    <!-- Navbar Search -->
    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>

    <!-- Navbar -->
    <ul class="navbar-nav ml-auto ml-md-0">
      <li class="nav-item dropdown no-arrow mx-1">
        <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-bell fa-fw"></i>
          <span class="badge badge-danger">9+</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item dropdown no-arrow mx-1">
        <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-envelope fa-fw"></i>
          <span class="badge badge-danger">7</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="messagesDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item dropdown no-arrow">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-user-circle fa-fw"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
          <a class="dropdown-item" href="#">Settings</a>
          <a class="dropdown-item" href="#">Activity Log</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Logout</a>
        </div>
      </li>
    </ul>

  </nav>

  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="sidebar navbar-nav bg-light2">
      <li class="nav-item active">
        <a class="nav-link" href="./AdminMain.adme">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>관리 메인페이지로</span>
        </a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="./Main.me">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>홈페이지 메인으로</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./AdminMemberList.adme">
          <i class="fas fa-fw fa-table"></i>
          <span>회원리스트</span>
        </a>
        
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-table"></i>
          <span>상품관리</span></a>
          <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="./ItemListAdminAction.adite">상품리스트</a>
          <a class="dropdown-item" href="./ItemInsertAdminForm.adite">상품등록</a>
          </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./AdminAskListAction.adask">
          <i class="fas fa-fw fa-table"></i>
          <span>문의관리</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./NoticeList.nb">
          <i class="fas fa-fw fa-table"></i>
          <span>공지게시판관리</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">
          <i class="fas fa-fw fa-table"></i>
          <span>쿠폰관리</span></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-table"></i>
          <span>주문내역</span></a>
         <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <a class="dropdown-item" href="./AdminOrderList.ador">주문현황</a>
         </div>
          
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./AdminSalesListAction.ads">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>매출관리</span></a>
      </li>
    </ul>

   



  <!-- Bootstrap core JavaScript-->
  <script src="./admin/vendor/jquery/jquery.min.js"></script>
  <script src="./admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="./admin/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <script src="./admin/vendor/chart.js/Chart.min.js"></script>
  <script src="./admin/vendor/datatables/jquery.dataTables.js"></script>
  <script src="./admin/vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="./admin/js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="./admin/js/demo/datatables-demo.js"></script>
  <script src="./admin/js/demo/chart-area-demo.js"></script>

</body>

</html>