package com.tracker;

import com.tracker.filter.MyCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@SpringBootApplication
public class RustTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RustTrackerApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private MyCorsFilter myCorsFilter;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().authorizeRequests()
				.anyRequest()
				.permitAll()
				.and().csrf().disable();
			http.addFilterBefore(myCorsFilter, ChannelProcessingFilter.class);
		}
	}

}
