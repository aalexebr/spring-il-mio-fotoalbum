<script>
//import
import axios from 'axios';
import AsideComp from '../../components/AsideAdminComp.vue';
   export default {
    data() {
      return{
        currentDayNum : null,
        currentMonthNum : null,
        currentMonthStr: null,
        currentYearNum: null,
        calendar:{
            January : 31,
            February : 28,
            March : 31,
            April : 30,
            May : 31,
            June: 30,
            July: 31,
            August : 31,
            September : 31,
            October : 31,
            November : 30,
            December : 31
        },
        months : ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
        weekdays: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        openMonthFlag : false,
        openMonth : null,
        openMonthStr : null
      }
    },
    methods: {
        //function
        getImagePath: function(image){
            return new URL (image, import.meta.url).href;
        },
        getYear(){
            const d = new Date();
            // console.log(d.getFullYear())
            return d.getFullYear();
        },
        gapYear(){
            const d = new Date();
            if((this.currentYearNum % 4) == 0){
                this.calendar.February = 29
                return true
            }
            else {
                return false
            }
        },
        getMonthString(){
            const d = new Date();
            this.currentMonthStr = this.months[this.currentMonthNum-1]
            // console.log(this.currentMonthStr, 'monthstr')
            // return this.months[d.getMonth()];
        },
        getDay(){
            const d = new Date();
            this.currentDayNum = d.getDate()
            // console.log(this.currentDayNum,'day')
            return d.getDate()
        },
        getWeekDay(){
            const d = new Date();
            // console.log(d.getDay(), this.weekdays[d.getDay()])
        },
        getMonthNumb(){
            const d = new Date();
            this.currentMonthNum = d.getMonth()+1
            // console.log(this.currentMonthNum,'month numb')
            // return d.getMonth()+1
        },
        getCurrentCalendarDay(i,monthString){
            if((i+1 == this.currentDayNum) && (monthString == this.currentMonthStr)){
                return true
            }
        },
        getPreviousCalendarDays(j,month,i){
            if((j+1 < this.currentMonthNum) || ((month == this.currentMonthStr) && (i+1 < this.currentDayNum))){
                return true
            }

        },
        getYear(){
            const d = new Date();
            this.currentYearNum = d.getFullYear()
            // console.log(d.getFullYear())
        },
        openMonthClick(month, i){
            if((month == this.currentMonthStr) || (i+1 > this.currentMonthNum)){
                this.openMonthFlag = true
                this.openMonth = i+1
                this.openMonthStr = this.months[i]
                // console.log(this.openMonthStr)
            }
        },
        monthClickCursor(month, i){
            if((month == this.currentMonthStr) || (i+1 > this.currentMonthNum)){
                return 'cursor'
                // console.log(this.openMonthStr)
            }
        },
        closeMonthView(){
            this.openMonthFlag = false
            this.openMonth = null
            this.openMonthStr = null
        },
        deprecatedDaysinMonthView(day,monthStr){
            if((day+1<this.currentDayNum) && (monthStr == this.currentMonthStr)){
                return true
            }
        },
        setWeekDays(year,month,day){
            const d = new Date(`${year}-${month}-${day+1}`);
            let dayofweek = this.weekdays[d.getDay()];
            return dayofweek.substring(0, 3);
        },
        nextMonth(){
            // this.openMonthStr = this.months[this.openMonth]
            if(this.openMonth < this.months.length){
                this.openMonthStr = this.months[this.openMonth]
                this.openMonth++ 
                console.log(this.openMonth,'month')
                console.log(this.months.length,'array')
            }
            // else{
            //     this.openMonth = this.months.length
            // }    
        },
        preMonth(){

        }
        // getip(){
        //     axios.get('https://api.ipify.org?format=json')
        //         .then(res=>{
        //             console.log(res)
        //         })
        // },

    },
    components: {
        //dichiarazione
		AsideComp

    },
    props:{
        //utilizzo per file padre
    },
    created(){
        this.getDay();
        this.getMonthNumb()
        this.getMonthString()
        // this.getip()
        this.getYear()

    }
    
  }
</script>


<template>
<div class="dashboard">

    <AsideComp/>
		
    <!-- inside -->
    <!-- <button class="btn btn-success"> next year</button> -->
    
    
    <!-- Tutti i mesi -->
    <div class="container mw-100 overflow-y-auto">
        
                <h3>
                 {{ currentYearNum }}
                </h3>
        <div class="row">
            <template v-if="!openMonthFlag">

                <div class="row align-items-center">
                    <div class="col-8 all-months">
                        <a href="" class="next-year">
                            Next Year
                        </a>
                        <a href="" class="current-month-button">
                            Current Month
                        </a>
                        
                    </div>
                    <div class="col-4">
                        <!-- bottone per chiudere tutto il calendario-->
                        <i class="fa-xl fa-solid fa-xmark"></i>
                        
                    </div>
                </div>
                <div 
                    @click="openMonthClick(month, j)"
                    class="calendar-box" v-for="(month, j) in months" :key="j">

                    <h6>
                        {{month}} {{ j+1 }}
                    </h6>

                    <div class="days-box">
                        <template v-if="gapYear()">
                            <div class="days" v-for="(day,i) in calendar[`${month}`]" :key="i"
                                :class="{'current': getCurrentCalendarDay(i,month),
                                'notselectable': getPreviousCalendarDays(j,month,i)}">
                            {{ i+1 }}
                            </div>
                        </template>
                        <template v-else>
                            <div class="days" v-for="(day,i) in calendar[`${month}`]" :key="i"
                                :class="{'current': getCurrentCalendarDay(i,month),
                                'notselectable': getPreviousCalendarDays(j,month,i)}">
                            {{ i+1 }}
                            </div>
                        </template>
                    </div>
                </div>
            </template>


            <!-- Mese aperto -->
            <template v-if="openMonthFlag">

                <div class="row align-items-center">
                    <div class="col-8">
                        <a href="" class="next-year">
                            da decidere
                        </a>
                        <a href="" class="current-month-button">
                            da decidere
                        </a>
                        <!-- bottone per chiudere -->
                        <i @click="closeMonthView" class="fa-xl fa-solid fa-xmark"></i>
                        
                    </div>
                    <div class="col-4">
                        
                    </div>
                </div>

                <!-- <h3>
                    {{ openMonthStr }}
                </h3> -->
                    
                <div class="row">
                    <div class="col-sm-10 col-md-8">
                        <div class="month-box">
                            
                            <div class="arrows">
                                <i @click="prevMonth()" class="fa-solid fa-caret-left"></i>
                                <h6>
                                    {{ openMonthStr }}
                                </h6>
                                <i @click="nextMonth()" class="fa-solid fa-caret-right"></i>
                            </div>
                            <div class="weekday">
                                <span v-for="j in 7">
                                    Weekday
                                </span>
                            </div>
                            
                            <div class="day-month-box">

                                <template v-if="gapYear()">
                                    <div class="month-days" v-for="(e,i) in calendar[openMonthStr]" :key="i"
                                        :class="{'current': getCurrentCalendarDay(i,openMonthStr),
                                        'notselectable': deprecatedDaysinMonthView(i, openMonthStr)}">
                                    {{ i+1 }}{{ setWeekDays(currentYearNum,openMonth,i) }}
                                    </div>
                                </template>
                                <template v-else>
                                    <div class="month-days" v-for="(e,i) in calendar[openMonthStr]" :key="i"
                                        :class="{'current': getCurrentCalendarDay(i,openMonthStr),
                                        'notselectable': deprecatedDaysinMonthView(i, openMonthStr)}">
                                    {{ i+1 }}{{ setWeekDays(currentYearNum,openMonth,i) }}
                                    </div>
                                </template>
                            </div>
                        </div>
                    </div>

                    <!-- aside -->
                    <div class="col-sm-2 col-md-4">
                        <div class="hours">
                            <div class="hour-box" v-for="x in 10">
                                hour available
                            </div>
                        </div>
                    </div>
                </div>

            </template>
        </div>
    </div>

</div>
    
</template>


<style lang="scss" scoped>
@use '../scss/variables.scss' as *;

.dashboard{
    padding: 0;
    margin: 0;
    display: flex;
    height: 100vh;
    background-color:#bbdbae;

    >*{
        padding: 0;
    }
    
    
    aside{
        width: 10%;
        height: 100vh;
		background-color:#303854;
        overflow-x: hidden;
        overflow-y: hidden;
		background-image: url(../img/wave-dashboard.png);
		background-position: bottom;
		background-size: cover;
		position: relative;

		
    }

    .dashboard-inside{
        width: 90%;
		height: 100vh;
		overflow-y: auto;

		.home-page{
			margin-top: 30px;
			text-align: center;
		}
    }
    
}
.all-months{
    >*{
        margin-right: 20px;
    }
    .current-month-button{
        height: 20px;
        padding: 10px;
        background-color: blue;
        color: white;
        border-radius: 10px;
        text-decoration: none;
    }
    .next-year{
        height: 20px;
        padding: 10px;
        background-color: green;
        color: white;
        border-radius: 10px;
        text-decoration: none;
    }
}
.col-4{
    position: relative;

    .fa-xmark{
        position: absolute;
        right: 20px;
        top: 0;
    }
}
.row{
    margin: 20px 5px;
    .calendar-box{
        //margin-top: 20px;
        width: calc((100% / 4));
        height: 200px;
        border: 1px solid black;
        //border-radius: 10px;
        h6{
            text-align: center;
            padding-top: 5px;
        }
        .days-box{
            margin-top: 10px;
            width: 100%;
            height: 80%;
            border: 2px solid grey;
            border-radius: 10px;
            display: flex;
            //justify-content: center;
            flex-wrap: wrap;

            .days {
                margin:0 5px;
                width: calc((100% / 7) - 5px);
                height: 20px;
                background-color: white;
                color: green;
                &.notselectable{
                    background-color: grey;
                    color: black;
                }
                &.current{
                    background-color: green;
                    color: white;
                }
            }
        }
    }
}

.col-8{
    position: relative;

    .next-year{
        height: 20px;
        padding: 10px;
        background-color: green;
        color: white;
        border-radius: 10px;
        text-decoration: none;
        margin-right: 20px;
    }
    .current-month-button{
        height: 20px;
        padding: 10px;
        background-color: blue;
        color: white;
        border-radius: 10px;
        text-decoration: none;
    }
    .fa-xmark{
        position: absolute;
        right: 5px;
        top: 11px;
    }
}

.row{
    //padding-top: 30px;
    .month-box{
        padding-bottom: 5px;
        //padding: 0 10px;
        //width: 100%;
        height: 350px;
        border: 1px solid black;
        border-radius: 10px;
        .arrows{
            >*{
                display: inline-block;
                padding: 0 10px;
            }
            text-align: center;
            padding-top: 10px;
        }
        .weekday{
                display: flex;
            >*{
                padding-right: 10px;
            }
        }
        .day-month-box{
            width: 100%;
            height: 80%;
            border: 2px solid grey;
            border-radius: 10px;
            display: flex;
            //justify-content: center;
            flex-wrap: wrap;
            .month-days {
                margin: 5px 10px;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 20px;
                width: calc((100% / 7) - 20px);
                height: 20px;
                background-color: white;
                color: green;
                
                &.notselectable{
                    background-color: grey;
                    color: black;
                }
                &.current{
                    background-color: green;
                    color: white;
                }
            }
            //questo si potrebbe fare solo su quelli correnti, ovviamente da sistemare
            .month-days:not(.notselectable):hover {
                background-color: lightgreen;
                border-radius: 10px;
            }
        }
    }

    .hours{
        max-height: 350px;
        display: flex;
        flex-direction: column;
        overflow-y: auto;
        .hour-box{
            margin-bottom: 10px;
            text-align: center;
            border: 2px solid green;
            height: 50px;
            width: 100%;
        }
    }
}



</style>