"use strict";(self["webpackChunklangsi"]=self["webpackChunklangsi"]||[]).push([[311],{4311:function(e,n,t){t.r(n),t.d(n,{default:function(){return s}});var a=function(){var e=this,n=e._self._c;return n("div",[n("h3",{staticStyle:{"text-align":"center",margin:"40px","margin-bottom":"200px"}},[e._v("绑定学员信息")]),n("van-form",{on:{submit:e.onSubmit}},[n("van-field",{attrs:{type:"number",name:"手机号",label:"手机号",placeholder:"学员登记时填写的手机号",rules:[{required:!0,message:"请填写"}]},model:{value:e.phoneNumber,callback:function(n){e.phoneNumber=n},expression:"phoneNumber"}})],1),n("div",{staticStyle:{margin:"16px"}},[n("van-button",{attrs:{round:"",block:"",type:"info"},on:{click:e.goPage}},[e._v("绑定")])],1)],1)},o=[],r={name:"bindUserInfo",data(){return{username:"",phoneNumber:""}},methods:{onSubmit(e){console.log("submit",e)},goPage(){let e=this.phoneNumber;window.location.href=`http://q8vp3u.natappfree.cc/wx/my/authorize?phone=${e}`}}},u=r,i=t(1001),l=(0,i.Z)(u,a,o,!1,null,"c6e519a0",null),s=l.exports}}]);
//# sourceMappingURL=311.6c9a3f18.js.map