
# Trinetra

## Operations

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
        
        [worker_id, Aadhar_id, name, job designation, skill_type]

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
        [shift_id, shift_datetime, project_id]


      - attendance

        [shift_id, emp_id_space_sorted]
        
        - emp_id_space_sorted

          for example 

          1 2 7 6 8 are present on 12th feb 2018 in shift A 
          we can store

          "1 2 6 7 8" which can be processed
  


  - project 
    
    project = sum(shift) + sum(stages)
    
    shift = attendance(worker, user.contractor)
            + authentication(attendance(worker), user.contractor)

    - Authentication
	[shift_id, gen_worker_id, status]

  - report
    
    - attendance(shift)
    - authentication(shift)


## References

   - [Volley Tutorial](https://code.tutsplus.com/tutorials/an-introduction-to-volley--cms-23800)



## TODO

   - [x] create_account
   - [x] sign_in
   - [ ] read_user_detail
   - [ ] update_user_detail

   - [x] create_project
   - [x] read_projects
   - [ ] update_project
   - [ ] delete_project

   - [ ] add_user_project
   - [ ] remove_user_project

   - [ ] add_worker
   - [ ] remove_worker
   - [ ] update_worker
   - [ ] delete_worker

   - [ ] add_user_worker
   - [ ] remove_user_worker


   - [ ] create_shift
   - [ ] read_shift

   - [ ] create_attendence
   - [ ] read_attendence

   - [ ] integrate random function with Aadhar API
   - [ ] create_authentication
   - [ ] read_authentication














