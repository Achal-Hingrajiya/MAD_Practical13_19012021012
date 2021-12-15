package com.example.practical13_19012021012.sms_info

class OutboxMessages (var contactNo : String, var body : String){

    var id = idGeneration()

    companion object {

        var id : Long = 0

        fun idGeneration(): Long {
            id += 1
            return id
        }

        fun addSMS(sms : OutboxMessages){
            outboxSmsArr.add(sms)
        }
        var outboxSmsArr: ArrayList<OutboxMessages> = ArrayList()
    }
}
