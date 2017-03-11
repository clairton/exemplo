import Ember from 'ember';

export default Ember.Route.extend({

	model(){
		return this.get('store').createRecord('pessoa');
	},

	actions:{
		
		salvar(pessoa){
			let self = this;
			pessoa.save().then(() => {
				self.transitionTo('lista');
			});
		}			
		
	}
});
