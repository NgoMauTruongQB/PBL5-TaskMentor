import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function NotificationItem(props){
    let {classCode,className,content,teacherName} = props.Notification
    const {onPress} = props
    return (<TouchableOpacity onPress={onPress} style={{
        marginBottom:5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection:'row',
       // alignItems:"center"
      // alignItems:"center",
       marginEnd:10,
    }}>
       <View>
       <Image style={{
            width:50,
            height:50,
            resizeMode:'cover',
            borderRadius:10,
            marginRight:15,
            marginStart:10
        }} source={{
            uri: 'https://inkythuatso.com/uploads/thumbnails/800/2022/03/anh-dai-dien-facebook-dep-cho-nam-30-28-16-26-50.jpg'
        }}>
        </Image>
       </View>
      <View style={{
        flexDirection:'column',
      }}>
      <Text style={{
        color:'black',
        fontSize:FontSize.h5,
        fontWeight:'bold'
      }}>{teacherName}</Text>
      <View style={{
        marginRight:60
      }}>
      <Text style={{
        paddingEnd:10,
        color:color.inactive,
        fontSize:FontSize.h5,
        marginRight:10,
      }}>{content}</Text>
      </View>
    
      </View>

    </TouchableOpacity>)
} export default NotificationItem