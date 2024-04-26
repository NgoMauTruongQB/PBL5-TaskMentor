// import React, { useState, useEffect } from "react";
// import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, SafeAreaView,Switch } from "react-native";
// import { image, icons, color, FontSize } from "../constants";
// import Icon from 'react-native-vector-icons/FontAwesome';
// import { isValidEmail, isValidPassword } from "../utilies/Validations"
// import {
//     user as UserReponsitory,
//     population as PopulationReponsitory
// } from '../repositories'
// import { UIHeader } from "../components";
// import FontAwesomeIcon from 'react-native-vector-icons/FontAwesome';


// // function Profile(props){
// //     const [user, setUser] = useState({})
// //     const{ email ,userid,address,gender,username,url,phone,registeredDate } = user
// //     useEffect(() => {
// //         UserReponsitory.getUserDetail()
// //         .then(responseUser => setUser(responseUser))
// //     }, [])  

// //     return <SafeAreaView style={{
// //         flex: 1,
// //         marginTop: 40,

// //     }}> 
// //     {/* <Image source={{uri:url}}></Image> */}

// //         <Text> Email: {email},
// //         {userid},{address} </Text>
// //         <Image style={{
// //         width:40,
// //         height:40
// //     }} source={{
// //         uri: url
// //     }}>

// //     </Image>
// //     </SafeAreaView>
// // } export default Profile
// const SettingItem = ({ imageSource, title, onPress }) => (
//     <TouchableOpacity onPress={onPress}>
//         <View style={{
//         flexDirection: 'row',
//         alignItems: 'center',
//         height: 60,
//     }}>
//         <Image source={imageSource} style={{
//             width: 30,
//             height: 30,
//             resizeMode: 'cover',
//             //borderRadius: 50,
//             backgroundColor: 'transparent',
//             marginRight: 15,
//             marginStart: 20,
//             alignSelf: 'center',
//             tintColor:'#81b0ff',
//         }} />
//         <Text style={{
//             fontSize: FontSize.h6,
//             paddingStart: 10
//         }}>{title}</Text>
//         <View style={{ flex: 1 }}></View>
//         <Icon name="chevron-right" color='black' size={17} style={{ paddingEnd: 10 }}  />
//     </View>
//     </TouchableOpacity>
// );
// function Profile(props) {
//     const[isEnabled, setisEnabled]= useState(true)
//     const [user, setUser] = useState({})
//     const{ email ,userid,address,gender,username,url,phone,registeredDate } = user
//     useEffect(() => {
//                  UserReponsitory.getUserDetail()
//                  .then(responseUser => setUser(responseUser))
//              }, []) 
//     const settings = [
//         //{ imageSource: image.moon, title: 'Dark Mode' },
//         { imageSource: image.updateProfile, title: 'Update Profile' },
//         { imageSource: image.changepassword, title: 'Change Password' },
//         { imageSource: image.about, title: 'About' },
//         { imageSource: image.logout, title: 'Logout' }
//     ];
//     return <View style={{
//         flex: 1,
//         marginTop: 40,
//     }}>
//         <UIHeader title={'Settings'}
//             // leftIconName={'arrow-left'}
//             rightIconName={'search'}
//             // onPressLeftIcon={() => {
//             //     alert('back')
//             // }}
//             onPressRightIcon={() => {
//                 alert('next')
//             }}
//         ></UIHeader>
//         <View style={{
//             flexDirection: 'row',
//             marginEnd: 20,
//             paddingRight: 20,
//         }}>
//             <Image style={{
//                 marginTop: 20,
//                 width: 100,
//                 height: 100,
//                 resizeMode: 'cover',
//                 borderRadius: 50,
//                 marginRight: 15,
//                 marginStart: 20,
                
//             }} source={{
//                 uri: url
//             }}></Image>
//             <View style={{
//                 flexDirection: 'column',
//                 // backgroundColor:'red',
//                 justifyContent: "center",
//                 //alignItems:"center",
//                 marginEnd: 60,
//                 paddingRight: 20
//             }}>
//                 <View>
//                     <Text style={{
//                         fontWeight: 'bold',
//                         fontSize: FontSize.h3
//                     }}>{username}</Text>
//                 </View>
//                 <View>
//                     <Text style={{
//                         paddingRight: 20
//                     }}>
//                         {email}
//                         {/* Tôi muốn thay đổi thế giới. Nhưng tôi phát hiện ra điều duy nhất bạn có thể chắc chắn làm thay đổi là chính bản thân mình. */}
//                     </Text>
//                 </View>
//             </View>
//         </View>
//         <View style={{
//             marginTop: 20,
//             height: 2,
//             backgroundColor: "#EBEBEB",

//         }}>
//         </View>
//         <View style={{
//         flexDirection: 'row',
//         alignItems: 'center',
//         height: 60,
//     }}>
//         <Image source= {image.moon} style={{
//             width: 30,
//             height: 30,
//             resizeMode: 'cover',
//             //borderRadius: 50,
//             backgroundColor: 'transparent',
//             marginRight: 15,
//             marginStart: 20,
//             alignSelf: 'center',
//             tintColor:'#81b0ff',
//         }} />
//         <Text style={{
//             fontSize: FontSize.h6,
//             paddingStart: 10
//         }}>Dark Mode</Text>
//         <View style={{ flex: 1 }}></View>
//         <Switch
//         trackColor={{false: color.inactive, true: '#81b0ff'}}
//         thumbColor={isEnabled ? 'white': color.inactive}
//         ios_backgroundColor="#3e3e3e"
//         onValueChange={()=>{
//             setisEnabled(!isEnabled)
//         }}
//         value={isEnabled}
//       />
//         {/* <Icon name="chevron-right" color='black' size={17} style={{ paddingEnd: 10 }}  /> */}
//     </View>
//         <View style={{ flex: 1,}}>
//             {settings.map((setting, index) => (
//                 <SettingItem
//                     key={index}
//                     imageSource={setting.imageSource}
//                     title={setting.title}
//                     onPress={() => {
//                         if(setting.title === 'Update Profile'){
//                                 alert('profile')
//                         } else if(setting.title === 'Change Password'){
//                             //navigate('ChangePassword')
//                         } else if (setting.title === 'About'){
//                                 alert('about')
//                         } else if(setting.title === 'Logout'){
//                                 alert("logout")
//                         }
//                     }} // Đổi hành động tương ứng
//                 />
//             ))}
//         </View>
//     </View>
// } export default Profile