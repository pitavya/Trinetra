
# Trinetra

# Operations

```JSON

    {
	user : {

		create_user(),
		sign_in(),

		admin : {
			create_project(),
			add_user(),
			view_attendence(),
			view_report()
		},

		supervisor : {
			add_user(),
			view_attendence(),
			view_report()
		},

		contractor : {
			add_worker(),

			create_attendence(),
			create_report(),
			
			create_shift(),			
		},
	},
	
	project : {
		
		
	},
	
	shift : {


	}

	worker : {

	},

	attendence : {

	},

	verification : {

	}

    }

```
  
### DATABASE SCHEMA
	
  - user [admin, supervisor, contractor]

    [user_id, name, email, password, adhar_id, ac_type]

    - admin

      - admin
      - project
      - supervisor
      - contractor

    - supervisor

      - contractor


    - contractor

      - worker
        
        [worker_id, adhar_id, name, job designation, skill_type]

        - skill type

          1. skilled      permanent
          2. semi-skilled permanent
          3. unskilled    permanent
          4. skilled      temporary
          5. semi-skilled temporary
          6. unskilled    temporary
          7. skilled      daily_wage
          8. semi-skilled daily_wage
          9. unskilled    daily_wage
        

      - shift
        [shift_id, shift_datatime, project_id]


      - attendence        

        [shift_id, emp_id_space_sorted]
        
        - emp_id_space_sorted

          for example 

          1 2 7 6 8 are present on 12th feb 2018 in shift A 
          we can store

          "1 2 6 7 8" which can be processed
  


  - project 
    
    project = sum(shift) + sum(stages)
    
    shift = attendence(worker, user.contractor) 
            + authentication(attendence(worker), user.contractor)

    - Authentication
	[shift_id, gen_worker_id, status]

  - report
    
    - attendence(shift)
    - authentication(shift)


