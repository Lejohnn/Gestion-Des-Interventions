<template>
  <VBadge dot location="bottom right" offset-x="3" offset-y="3" color="success" bordered >
    <VAvatar class="cursor-pointer" color="primary" variant="tonal" >
      <VImg :src="avatar1" />

      <!-- SECTION Menu -->
      <VMenu activator="parent" width="230" location="bottom end" offset="14px" >
        <VList>
          <!-- 👉 User Avatar & Name -->
          <VListItem>
            <template #prepend>
              <VListItemAction start>
                <VBadge dot location="bottom right" offset-x="3" offset-y="3" color="success" >
                  <VAvatar color="primary" variant="tonal" >
                    <VImg :src="avatar1" />
                  </VAvatar>
                </VBadge>
              </VListItemAction>
            </template>

            <VListItemTitle class="font-weight-semibold">
              {{pseudo}}
            </VListItemTitle>
            <VListItemSubtitle> {{type}} </VListItemSubtitle>
          </VListItem>
          <VDivider class="my-2" />

          <!-- 👉 Logout -->
          <VListItem @click.prevent="logout">
            <template #prepend>
              <VIcon class="me-2" icon="mdi-logout" size="22" />
            </template>

            <VListItemTitle>Déconnexion</VListItemTitle>
          </VListItem>
        </VList>
      </VMenu>
      <!-- !SECTION -->
    </VAvatar>
  </VBadge>
</template>

<script>

  import axios from "axios";
  import Swal from 'sweetalert2'
  import avatar1 from '@images/avatars/avatar-1.png'

  export default {
    setup() {
      return {
        avatar1,
      }
    },

    data(){
      return {
        type: '',
        pseudo: '',
      }
    },

    created() {
      let account = JSON.parse(localStorage.getItem("account"));
      if (account){
        if (account.created_by == 'etudiant') {
        this.type = "Etudiant";
        this.pseudo = account.matricule;
      } 
      else {
        this.type = "Personnel";
        this.pseudo = account.nom;
      }
      } 
      
    },

    methods: {
      async logout(){
        Swal.fire({
          title: 'Êtes vous sûres ?',
          html: "Hello, s'il vous plait souhaitez vous vraiment détruire votre session ? Cette action est irréversible.",
          showDenyButton: true,
          showCancelButton: false,
          confirmButtonText: "Oui je souhaite.",
          denyButtonText: `Annuler`,
        }).then((result) => {
          if (result.isConfirmed) {
            Swal.fire({
              'icon' : 'success',
              'text' : "Déconnexion éffectuée avec succès.",
              'toast' : true,
              'position' : 'top-end',
              'showCancelButton' : false,
              'showConfirmButton' : false,
              'timer': 5000,
              'timerProgressBar': true
            });
            this.$router.push("/login");
            window.localStorage.clear();
          } 
          else if (result.isDenied) {}
        })
      }
    }
  }
</script>
